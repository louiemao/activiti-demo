/**
 * Created by MaoLiang on 2016/5/12.
 */
function CloseWindow(action) {
    if (window.CloseOwnerWindow) {
        return window.CloseOwnerWindow(action);
    } else if (window.parent) {
        window.parent.open('', '_parent', '');
        // window.parent.opener = null;
        window.parent.close();
    } else if (window) {
        // window.opener = "test"
        window.close();
    } else {
        top.close();
    }
}