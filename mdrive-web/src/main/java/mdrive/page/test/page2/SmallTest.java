package mdrive.page.test.page2;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 01.04.12
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 */
public class SmallTest {
    public Date date = new Date();

    public void verify() {
        MyPropertyModel<Date> myPropertyModel = new MyPropertyModel<Date>(SmallTest.this.date);
    }

    public static void main(String[] args) {
        new SmallTest().verify();
    }
}
