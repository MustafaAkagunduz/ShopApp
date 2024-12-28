import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Anasayfa from './pages/Anasayfa';
import ForgetPassword from './pages/ForgetPassword';
import UserDashboard from './pages/UserDashboard';;
import SignUp from './pages/SignUp'; // importing register
import './index.css';
import Community from './pages/Community';
import MerchantDashboard from './pages/MerchantDashboard';
import Login from './pages/Login';
import ModeratorDashboard from './pages/ModeratorDashboard';
import AdminDashboard from './pages/AdminDashboard';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(

  <React.StrictMode>
    <Router>
      <Routes>
      
        <Route  exact path="/" element={<Anasayfa />} />
        <Route path="/login" element={<Login />} />
        <Route path="/community" element={<Community />} />
        <Route  path="/userdashboard" element={<UserDashboard />} />
        <Route path="/forget" element={<ForgetPassword />} />
        <Route path="/register" element={<SignUp />} /> // Register
        <Route path="/merchantdashboard" element={<MerchantDashboard />} />
        <Route path="/moderatordashboard" element={<ModeratorDashboard />} />
        <Route path="/admindashboard" element={<AdminDashboard />} />
       </Routes>
    </Router>
  </React.StrictMode>
);
