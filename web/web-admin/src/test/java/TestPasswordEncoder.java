import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 周书胜
 * @date 2023年03月11 10:01
 */
public class TestPasswordEncoder {
    @Test
    public void test1() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bCryptPasswordEncoder.encode("wyqhz581");
        System.out.println(pwd);

        /**4
         * $2a$10$592TYYQGBpmPncXhth/.P.S1LoXdV3xFJVML8WeyFey5Beou27Uza
         * $2a$10$4gJKhHc4lZsVFoT2K9iCzOQJBvqBnASYQCcTBCaqNOM1s.IDZupgW
         * $2a$10$7N/bnkc.L5.o1c/e8uYPvOCmCWwWmgahWRyE8sWnKPR5qRc5AvOeu
         * $2a$10$a9KjV7QHLRy45No92eLJA.JCgda9ddkzb19jIvMXWUBV4.PcLaV8K
         * $2a$10$4.XFAjmxZCPP2M/L2EgcSeBXJHcBWnp5cRKz0qsFhjWIQf/2kDMLC
         * $2a$10$hMYCESdumZvR8Xgka.vwZeLQsFL9O0nr7.wTsSlmY/EhR3GqR19r.
         */

        boolean matche1 = bCryptPasswordEncoder.matches("wyqhz581", "$2a$10$592TYYQGBpmPncXhth/.P.S1LoXdV3xFJVML8WeyFey5Beou27Uza");
        boolean matche2 = bCryptPasswordEncoder.matches("wyqhz581", "$2a$10$4gJKhHc4lZsVFoT2K9iCzOQJBvqBnASYQCcTBCaqNOM1s.IDZupgW");
        boolean matche3 = bCryptPasswordEncoder.matches("wyqhz581", "$2a$10$7N/bnkc.L5.o1c/e8uYPvOCmCWwWmgahWRyE8sWnKPR5qRc5AvOeu");
        System.out.println("matche1=" + matche1);
        System.out.println("matche2=" + matche2);
        System.out.println("matche3=" + matche3);
    }
}
