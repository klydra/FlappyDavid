public interface Communication {
    void onAuxiliaryMessage(String message);

    void onAuthenticationRegistered(String account);
    void onAuthenticationTaken();
    void onAuthenticationUnregistered();

    void onSessionStart();
    void onSessionReady(String account);
    void onSessionUnReady(String account);
    void onSessionUserJoined(String account, String username);
    void onSessionUserLeft(String account);
    void onSessionPositionUpdate(String account, int positionY);
    void onSessionAvatarUpdate(String account, Byte avatar);
    void onSessionObstacle(int position);
}
