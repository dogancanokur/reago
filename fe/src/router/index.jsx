import {createBrowserRouter} from "react-router-dom";
import {Home} from "@/pages/Home/index.jsx";
import {SignUp} from "@/pages/SignUp/index.jsx";
import App from "@/App.jsx";
import {Activation} from "@/pages/Activation/activation.jsx";
import {UserListPage} from "@/pages/User/UserListPage.jsx";
import {UserPage} from "@/pages/User/UserPage.jsx";
import {ForUserClassComponent} from "@/pages/User/UserPageClassComponent.jsx";

export default createBrowserRouter([
    {
        path: "/",
        Component: App,
        // errorElement: <div>404</div>,
        children: [
            {path: "/", Component: Home},
            {path: "/users", Component: UserListPage},
            {path: "/signup", Component: SignUp},
            {path: "/activation/:token", Component: Activation},
            {path: "/user/:userId", Component: UserPage},
            {path: "/userTest/:userId", Component: ForUserClassComponent}
        ]
    }
]);
