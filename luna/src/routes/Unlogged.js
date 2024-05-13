import { Outlet, Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

const Unlogged = () => {
    return !AuthService.getCurrentUser() ? <Outlet /> : <Navigate to="/shop" replace/>;
};

export default Unlogged;