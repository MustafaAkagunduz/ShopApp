import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = () => {
    if (!email || !password) {
      setError('Username and password are required.');
      return;
    }
    setError(''); // Clear the error message

    // Login request to API
    fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email,
        password: password
      })
    })
    .then(response => response.json())
    .then(data => {
      // Save token and go to searchbar site if it's valid
      if (data.token) {
        window.location.href = '/searchbar';
      } else {
        setError('Invalid credentials. Please try again.');
      }
    })
    .catch(error => console.error('Login Error:', error));
  };

  const handleForgotPassword = () => {
    navigate('/forget');
    // Direct to forgot page
  };

  const handleRegister = () => {
    navigate('/register');
    // Direct to register page
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-300">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-700">ShopSmart</h1>
      </div>
      <div className="bg-gray-200 p-8 rounded-lg shadow-lg w-96">
        <h2 className="text-2xl font-bold mb-4 text-center">Login</h2>
        {error && <div className="bg-red-100 text-red-600 p-2 mb-4">{error}</div>}
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="text"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 "
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-md mb-2" onClick={handleLogin}>
          Login
        </button>
        <button className="w-full bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-md mb-2" onClick={handleRegister}>
          Register
        </button>
        <button className="w-full text-blue-500 hover:underline mb-2" onClick={handleForgotPassword}>
          Forgot password?
        </button>
      </div>
    </div>
  );
}

export default Login;
