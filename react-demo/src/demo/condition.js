import {AdminPanel} from "../components/AdminPanel";
import {LoginForm} from "../components/LoginForm";
export function Condition(){
    let isLoggedIn=false;
    let content;
    if (isLoggedIn) {
        content = <AdminPanel />;
    } else {
        content = <LoginForm />;
    }
    return (
        <div>
            {content}
        </div>
    );
}
