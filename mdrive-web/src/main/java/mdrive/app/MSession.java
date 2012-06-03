package mdrive.app;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * User: andrey.osipov
 */
public class MSession extends AuthenticatedWebSession {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MSession.class);

    private String user;

    /**
     * id of GeoObjectBean, which represents the location of current user
     */
    private Long userLocationStreetId;

    //may be empty, that's why we use ..StreetId
    private Long userLocationBuildingId;

    public MSession(Request request) {
        super(request);
    }

    public static MSession get() {
        return (MSession) Session.get();
    }

    /**
     * authenticate if username == password
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean authenticate(String username, String password) {
        if (username.equalsIgnoreCase(password)) {
            user = username;
            return true;
        }
        return false;
    }

    @Override
    public Roles getRoles() {
        if (isSignedIn()) {
            // If the user is signed in, they have these roles
            return new Roles(Roles.ADMIN);
        }
        return null;
    }

    public String getUser() {
        return user;
    }

    public Long getUserLocationStreetId() {
        return userLocationStreetId;
    }

    public void setUserLocationStreetId(Long userLocationStreetId) {
        this.userLocationStreetId = userLocationStreetId;
    }

    public Long getUserLocationBuildingId() {
        return userLocationBuildingId;
    }

    public void setUserLocationBuildingId(Long userLocationBuildingId) {
        this.userLocationBuildingId = userLocationBuildingId;
    }
}