const express = require("express");
const ws = require("ws");
const crypto = require("crypto");

const app = express();

const wsServer = new ws.Server({ noServer: true });
wsServer.binaryType = "blob";

const server = app.listen(80);
server.on("upgrade", (request, socket, head) => {
  wsServer.handleUpgrade(request, socket, head, (socket) => {
    wsServer.emit("connection", socket, request);
  });
});

let game = false;
let connections_accounts = new Map();
let accounts_users = new Map();
let accounts_ready = new Map();
let accounts_avatar = new Map();

wsServer.on("connection", (socket) => {
  console.log("CONNECTED")

  const LENGTH_ACCOUNT = 36;
  const LENGTH_POSITION = 450;
  const LENGTH_DELAY = 2000;

  const TYPE_AUXILIARY = 1;
  const TYPE_AUTHENTICATION = 2;
  const TYPE_SESSION = 3;

  const OP_JOINED = 3;
  const OP_LEFT = 4;
  const OP_AVATAR = 6;

  /* socket.on("close", (socket) => {
    console.log("DISCONNECTED")

    let account = connections_accounts[socket];
    accounts_avatar.delete(account);
    accounts_ready.delete(account);
    let username = accounts_users.get(account);
    accounts_users.delete(account);
    connections_accounts.delete(socket);

    broadcast(TYPE_SESSION, OP_LEFT, account);

    console.log("CLEANUP : " + username);
  }) */

  socket.on("message", (message) => {
    switch (message[0]) {
      case TYPE_AUXILIARY:
        break;

      case TYPE_AUTHENTICATION:
        const OP_REGISTER = 1;
        const OP_TAKEN = 2;
        const OP_BUSY = 3;
        const OP_UNREGISTER = 4;

        switch (message[1]) {
          case OP_REGISTER:
            if (game) {
              socket.send(header(TYPE_AUTHENTICATION, OP_BUSY));
              return;
            }

            let account = Buffer.from(crypto.randomUUID());
            let username = getContent(message).toString();

            let exists = false;

            accounts_users.forEach((value, _) => {
              if (value === username) {
                exists = true;
              }
            })

            if (exists) {
              socket.send(header(TYPE_AUTHENTICATION, OP_TAKEN));
              return;
            }

            connections_accounts.set(socket, account);
            accounts_users.set(account, username);
            accounts_ready.set(account, false);
            accounts_avatar.set(account, Buffer.alloc(4));

            socket.send(Buffer.concat([header(TYPE_AUTHENTICATION, OP_REGISTER), account]));

            console.log("REGISTERED : " + username);

            broadcast(
              TYPE_SESSION,
              OP_JOINED,
              Buffer.concat([account, Buffer.from(username)])
            );

            accounts_users.forEach((value, key) => {
              socket.send(
                  Buffer.concat([
                    header(TYPE_SESSION, OP_JOINED),
                    key,
                    Buffer.from(value),
                  ])
              );
            });

            accounts_avatar.forEach((value, key) => {
              socket.send(
                  Buffer.concat([header(TYPE_SESSION, OP_AVATAR), key, value])
              );
            });
            return;

          case OP_UNREGISTER:
            let account_ = connections_accounts.get(socket);
            accounts_avatar.delete(account_);
            accounts_ready.delete(account_);
            let username_ = accounts_users.get(account_);
            accounts_users.delete(account_);
            connections_accounts.delete(socket);

            broadcast(TYPE_SESSION, OP_LEFT, account_);

            socket.send(header(TYPE_AUTHENTICATION, OP_UNREGISTER));

            socket.close();

            console.log("UNREGISTERED : " + username_);
            return;
        }
        return;

      case TYPE_SESSION:
        const OP_READY = 1;
        const OP_UNREADY = 2;
        // const OP_JOINED;
        // const OP_LEFT;
        const OP_UPDATE = 5;
        // const OP_AVATAR;
        const OP_START = 7;
        const OP_OBSTACLE = 8;

        switch (message[1]) {
          case OP_READY:
            let account = connections_accounts.get(socket);
            accounts_ready.set(account, true);

            broadcast(TYPE_SESSION, OP_READY, account);

            console.log("READY : " + accounts_users.get(account));

            let all_ready = true;

            accounts_ready.forEach((value, _) => {
              if (!value) {
                all_ready = false;
              }
            });

            if (all_ready) {
              console.log("STARTING");

              broadcast(TYPE_SESSION, OP_START);
              game = true;
              pipes(TYPE_SESSION, OP_OBSTACLE, LENGTH_POSITION, LENGTH_DELAY);
            }
            return;

          case OP_UNREADY:
            let account_ = connections_accounts.get(socket);
            accounts_ready.set(account_, false);

            let all_unready = true;

            accounts_ready.forEach((value, _) => {
              if (value) {
                all_unready = false;
              }
            });

            if (all_unready) {
              game = false;
            }

            broadcast(TYPE_SESSION, OP_UNREADY, account_);

            console.log("UNREADY : " + accounts_users.get(account_));
            return;

          case OP_UPDATE:
            let content = getContent(message)

            broadcast(
              TYPE_SESSION,
              OP_UPDATE,
              Buffer.concat([connections_accounts.get(socket), content])
            );
            return;

          case OP_AVATAR:
            let account__ = connections_accounts.get(socket);
            let content_ = getContent(message);
            broadcast(
              TYPE_SESSION,
              OP_AVATAR,
              Buffer.concat([account__, content_])
            );
            accounts_avatar.set(account__, content_);

            console.log("AVATAR : " + accounts_users.get(account__));
            return;
        }
    }
  });
});

function getContent(buffer) {
  return buffer.slice(2);
}

function header(type, operation) {
  let message = Buffer.alloc(2);
  message.writeUInt8(type);
  message.writeUInt8(operation, 1);
  return message;
}

function broadcast(type, operation, content) {
  let broadcast;
  if (typeof content === "undefined") {
    broadcast = header(type, operation);
  } else {
    broadcast = Buffer.concat([header(type, operation), content]);
  }

  connections_accounts.forEach((_, key) => {
    key.send(broadcast);
  });
}

function pipes(type, operation, possibilities, delay) {
  if (game) {
    let value = Math.floor(Math.random() * possibilities - 1) + 1

    let offset = Math.ceil(value / 255)
    let buffer = Buffer.alloc(offset);
    buffer.writeUInt8(value % 256, offset - 1);

    broadcast(type, operation, buffer);

    console.log("OBSTACLE : " + value);

    setTimeout(() => pipes(type, operation, possibilities, delay), delay);
  }
}
