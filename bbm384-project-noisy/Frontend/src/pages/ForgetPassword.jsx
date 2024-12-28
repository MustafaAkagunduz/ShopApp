import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ForgetPassword = () => {
  const [error, setError] = useState('');
  const [email, setEmail] = useState('');
  const navigate = useNavigate();

  const handleForgot = async () => {
    if (!email) {
      setError('E-mail field cannot be empty.');
      return;
    }
    try {
      await axios.get(`http://localhost:8080/customer/password/recovery?email=${email}`);
      setError('');
      navigate('/login');
    } catch (error) {
      setError('An error occurred. Please try again later.');
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="bg-gray-200 p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-2xl font-bold mb-4 text-center text-black">Change your password!</h1>
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="email"
          placeholder="Your e-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        
        <button
          className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-md mb-2"
          onClick={handleForgot}
        >
          Send the request
        </button>
        {error && <div className="bg-yellow-200 text-yellow-800 p-2 mb-4">{error}</div>}
        <button
          className="w-full bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-md"
          onClick={() => navigate('/login')}
        >
          Back to Login
        </button>
      </div>
    </div>
  );
};

export default ForgetPassword;
