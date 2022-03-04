package template;

/**
 * @author Catch
 * @since 2022-03-04
 */
public abstract class AbstractClass {

    public final void method() {
        System.out.println("do step 1");
        // ....
        m1();
        System.out.println("do step 2");
        // ....
        m2();
        System.out.println("do step 3");
        // ...
    }

    public abstract void m1();

    public abstract void m2();

}
