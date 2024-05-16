import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Shop from './pages/Shop';
import Login from "./pages/Login";
import Register from "./pages/Register";
import RecoverPassword from "./pages/RecoverPassword";
import BookPage from "./pages/BookPage";
import Cart from "./pages/Cart";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Shop />} />
                <Route path="/shop" element={<Shop />} />
                <Route path="/Login" element={<Login />} />
                <Route path="/Register" element={<Register />} />
                <Route path="/PasswordRecovery" element={<RecoverPassword />} />
                <Route path="/BookPage" element={<BookPage/>} />
                <Route path="/cart" element={<Cart/>} />
            </Routes>
        </Router>
    );
}

export default App;
