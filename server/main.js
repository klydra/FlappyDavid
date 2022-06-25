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
  const LENGTH_ACCOUNT = 36;
  const LENGTH_POSITION = 450;
  const LENGTH_DELAY = 2.5;

  const TYPE_AUXILIARY = 1;
  const TYPE_AUTHENTICATION = 2;
  const TYPE_SESSION = 3;

  const OP_JOINED = 3;
  const OP_LEFT = 4;
  const OP_AVATAR = 6;

  socket.on("message", (message) => {
    switch (message[0]) {
      case TYPE_AUXILIARY:
        break;

      case TYPE_AUTHENTICATION:
        const OP_REGISTER = 1;
        const OP_UNREGISTER = 2;

        switch (message[1]) {
          case OP_REGISTER:
            let account = Buffer.from(crypto.randomUUID());
            let username = getContent(message).toString();

            accounts_users.forEach((value, key) => {
              socket.send(
                Buffer.concat([
                  header(TYPE_SESSION, OP_AVATAR),
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

            connections_accounts.set(socket, account);
            accounts_users.set(account, username);
            accounts_ready.set(account, false);
            accounts_avatar.set(account, Buffer.alloc(0));

            broadcast(
              TYPE_SESSION,
              OP_JOINED,
              Buffer.concat([account, Buffer.from(username)])
            );

            socket.send(header(TYPE_AUTHENTICATION, OP_REGISTER));
            return;

          case OP_UNREGISTER:
            let account_ = connections_accounts[socket];
            accounts_avatar.delete(account_);
            accounts_ready.delete(account_);
            accounts_users.delete(account_);
            connections_accounts.delete(socket);

            broadcast(TYPE_SESSION, OP_LEFT, account_);

            socket.send(header(TYPE_AUTHENTICATION, OP_UNREGISTER));

            socket.close();
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
            let account = connections_accounts[socket];
            accounts_ready.set(account, true);

            let all_ready = true;

            accounts_ready.forEach((value, _) => {
              if (!value) {
                all_ready = false;
              }
            });

            if (all_ready) {
              broadcast(TYPE_SESSION, OP_START);
              pipes(TYPE_SESSION, OP_OBSTACLE, LENGTH_POSITION, LENGTH_DELAY);
              game = true;
            }

            broadcast(TYPE_SESSION, OP_READY, account);
            return;

          case OP_UNREADY:
            let account_ = connections_accounts[socket];
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
            return;

          case OP_UPDATE:
            broadcast(
              TYPE_SESSION,
              OP_UPDATE,
              Buffer.concat([connections_accounts[socket], getContent(message)])
            );

            return;

          case OP_AVATAR:
            let account__ = connections_accounts[socket];
            let content = getContent(message);
            broadcast(
              TYPE_SESSION,
              OP_UPDATE,
              Buffer.concat([account__, content])
            );
            accounts_avatar.set(account__, content);

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
  message.writeInt8(type);
  message.writeInt8(operation);
  return message;
}

function broadcast(type, operation, content) {
  let broadcast = Buffer.concat([header(type, operation), content]);

  connections_accounts.forEach((_, key) => {
    key.send(broadcast);
  });
}

function pipes(type, operation, possibilities, delay) {
  if (game) {
    broadcast(type, operation, Math.floor(Math.random() * possibilities));
    setTimeout(() => pipes(type, operation, possibilities, delay), delay);
  }
}
