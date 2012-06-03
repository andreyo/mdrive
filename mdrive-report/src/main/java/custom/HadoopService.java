package custom;

/**
 * User: andrey.osipov
 * Date: 4/26/12
 * Time: 5:13 PM
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HadoopService {

    private Configuration config = null;
    private HTable table = null;


/*    public void configure() {

        config = HBaseConfiguration.create();
        //config.set("hbase.zookeeper.quorum", "hadoop-master.cht.local:8020"); // TODO: Externalize
        try {
            //config.addResource("hbase-site.xml");
            table = new HTable(config, "auditLog");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }


    }


    private class XmlSerrializer implements Processor {

        //@Override
        public void process(Exchange exchange) throws Exception {
            Event event = (Event) exchange.getIn().getBody();
            StringWriter sw = new StringWriter();
            try {
                // serialize it TODO: serialize more complex class hierarchy
                JAXBContext jxb = JAXBContext.newInstance(LoginEvent.class);
                jxb.createMarshaller().marshal(event, sw);
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            exchange.getOut().setBody(sw.toString());
        }
    }

    private class HBaseInserter implements Processor {
        //@Override
        public void process(Exchange exchange) throws Exception {

            JAXBContext jxb = JAXBContext.newInstance(LoginEvent.class);
            InputStream is = new ByteArrayInputStream((exchange.getIn().getBody().toString().getBytes()));
            Event e = (Event) jxb.createUnmarshaller().unmarshal(is);

            //table = new HTable(config, "auditLog");
            Put p = new Put(createRowKey(e));

            p.add(Bytes.toBytes("basic"), Bytes.toBytes("classname"), e.getClass().getSimpleName().getBytes());
            p.add(Bytes.toBytes("basic"), Bytes.toBytes("timestamp"), e.getTime().toString().getBytes());
            p.add(Bytes.toBytes("basic"), Bytes.toBytes("tpaid"), e.getTpaId().toString().getBytes());
            p.add(Bytes.toBytes("basic"), Bytes.toBytes("textdetails"), e.getDetails().toString().getBytes());
            p.add(Bytes.toBytes("extended"), Bytes.toBytes("username"), e.getAuthor().toString().getBytes());

            table.put(p);
        }
    }

    private static byte[] createRowKey(Event e) {
        RowKey rowKey = new RowKey();
        rowKey.setReverseTimeStamp(Long.MAX_VALUE - e.getTime());
        rowKey.setTpaId(e.getTpaId());
        // TODO rowKey.setGroupId()
        // TODO rowKey.setSubgroupId()
        rowKey.setEventType(e.getClass().getSimpleName());
        rowKey.setEmployeeId(e.getAuthor()); // TODO insert proper value after
        // new Authentification and role
        // system incorporated
        return rowKey.getBytesAuto();
    }*/
}
