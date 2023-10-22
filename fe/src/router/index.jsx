import {createBrowserRouter} from "react-router-dom";
import {Home} from "../pages/Home/index.jsx";
import {SignUp} from "../pages/SignUp/index.jsx";
import App from "../App.jsx";
import {Activation} from "../pages/Activation/activation.jsx";


export default createBrowserRouter([
    {
        path: "/",
        Component: App,
        // errorElement: <div>404</div>,
        children: [
            {path: "/", Component: Home},
            {path: "/signup", Component: SignUp},
            {path: "/activation/:token", Component: Activation}
        ]
    }
]);
