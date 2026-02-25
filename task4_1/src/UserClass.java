import java.util.Comparator;
import java.util.Objects;

public class UserClass implements Comparable<UserClass>
{
    private int a;
   private String b;
    public UserClass(int a,String b){
        this.a=a;
        this.b=b;
    }

    public int getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "первое поле= "+getA()+" второе поле= "+getB();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this){
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        UserClass userClass=(UserClass) obj;
        return Integer.compare(a,userClass.getA())==0&&b.equals(((UserClass) obj).getB());
    }

    @Override
    public int hashCode() {

        return Objects.hash(a, b);
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    public int compareTo(UserClass o) {
        int cmp = Integer.compare(this.a, o.a);
        if (cmp == 0) {
            return this.b.compareTo(o.b);
        }
        return Integer.compare(this.a, o.a);
    }
}
