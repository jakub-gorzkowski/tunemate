import { Navigate, Outlet } from "react-router-dom";
import AuthenticationService from "../service/AuthenticationService";

const PrivateRoutes = () => {
    let auth = AuthenticationService.getCurrentUser();
    return auth ? <Outlet /> : <Navigate to = '/login' />;
};

export default PrivateRoutes;