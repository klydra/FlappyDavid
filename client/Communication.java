public interface Communication {
    void onAuxiliaryMessage(String message);

    void onAuthenticationRegistered();
    void onAuthenticationUnregistered();

    void onSessionStart();
    void onSessionUserJoined(byte[] account, String username);
    void onSessionPositionUpdate(byte[] account, int positionY);
    void onSessionAvatarUpdate(byte[] account, Byte avatar);
    void onSessionObstacle(int position);
}