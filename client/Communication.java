public interface Communication {
    void onAuxiliaryMessage(String message);

    void onAuthenticationRegistered();
    void onAuthenticationTaken();
    void onAuthenticationUnregistered();

    void onSessionStart();
    void onSessionReady(byte[] account);
    void onSessionUnReady(byte[] account);
    void onSessionUserJoined(byte[] account, String username);
    void onSessionUserLeft(byte[] account);
    void onSessionPositionUpdate(byte[] account, int positionY);
    void onSessionAvatarUpdate(byte[] account, Byte avatar);
    void onSessionObstacle(int position);
}
