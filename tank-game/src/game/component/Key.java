
package game.component;

//untuk fungsi klik keyboard
public class Key {

    /**
     * @return the key_right
     */
    public boolean isKey_right() {
        return key_right;
    }

    /**
     * @param key_right the key_right to set
     */
    public void setKey_right(boolean key_right) {
        this.key_right = key_right;
    }

    /**
     * @return the key_left
     */
    public boolean isKey_left() {
        return key_left;
    }

    /**
     * @param key_left the key_left to set
     */
    public void setKey_left(boolean key_left) {
        this.key_left = key_left;
    }

    /**
     * @return the key_j
     */
    public boolean isKey_j() {
        return key_j;
    }

    /**
     * @param key_j the key_j to set
     */
    public void setKey_j(boolean key_j) {
        this.key_j = key_j;
    }

    /**
     * @return the key_space
     */
    public boolean isKey_w() {
        return key_w;
    }

    /**
     * @param key_w
     */
    public void setKey_w(boolean key_w) {
        this.key_w = key_w;
    }

    /**
     * @return the key_k
     */
    public boolean isKey_k() {
        return key_k;
    }

    /**
     * @param key_k the key_k to set
     */
    public void setKey_k(boolean key_k) {
        this.key_k = key_k;
    }
    /**
     * @return the key_s
     */
    public boolean isKey_s() {
        return key_s;
    }

    /**
     * @param key_s the key_s to set
     */
    public void setKey_s(boolean key_s) {
        this.key_s = key_s;
    }
    private boolean key_right;
    private boolean key_left;
    private boolean key_s;
    private boolean key_j;
    private boolean key_w;
    private boolean key_k;
}
