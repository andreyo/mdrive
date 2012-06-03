package mdrive.geo;

import mdrive.geo.exception.GeoException;
import org.junit.Ignore;
import org.junit.Test;

public class CoordinatesResolverTest {

    @Ignore("Network is unreachable (at work)")
    @Test
    public void xmlUaRequestRuResponse() throws GeoException {
        CoordinatesResolver.getCoordinates("Киев", "Киквидзе", "13В");
    }
}
