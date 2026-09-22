package si.ros.RosKasa.soap;

public class VersionConflictException extends Exception {
    private final int serverVerzija;
    private final int localVerzija;
    private final int racunId;

    public VersionConflictException(int racunId, int localVerzija, int serverVerzija, String message) {
        super(message);
        this.racunId = racunId;
        this.localVerzija = localVerzija;
        this.serverVerzija = serverVerzija;
    }

    public int getServerVerzija() { return serverVerzija; }
    public int getLocalVerzija() { return localVerzija; }
    public int getRacunId() { return racunId; }
}
