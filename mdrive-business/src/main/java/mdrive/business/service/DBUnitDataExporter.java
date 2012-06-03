package mdrive.business.service;

import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 13.08.11
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public interface DBUnitDataExporter {

    void exportTables(String destinationFile) throws Exception;

    void exportTables(String[] tableNames, String destinationFile) throws Exception;

    void exportTables(String[] tableNames, String destinationFile, Charset charset) throws Exception;
}
