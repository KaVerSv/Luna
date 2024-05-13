import { Outlet, Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

const Logged = () => {
    return AuthService.getCurrentUser() ? <Outlet /> : <Navigate to="/" />;
};

export default Logged;