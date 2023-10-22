import {createBrowserRouter} from "react-router-dom";
import {Home} from "../pages/Home/index.jsx";
import {SignUp} from "../pages/SignUp/index.jsx";

export default createBrowserRouter([
    {path: "*", Component: Home},
    // {path: "/", Component: Home, errorElement: <PageNotFound/>},
    {path: "/signup", Component: SignUp}
])
