package base.domain.fnl;

/**
 * @author : guochengsen@dongao.com
 * @date :
 */
public class FinalReferenceEscapeExample {

    public static final FinalReferenceEscapeExample escape = new FinalReferenceEscapeExample();

    private FinalReferenceEscapeExample() {
        System.out.println("construction method exec...");
    }

}
