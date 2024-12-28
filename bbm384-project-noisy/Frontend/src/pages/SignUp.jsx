import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function SignUp() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [role, setRole] = useState('');
  const [name, setName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [username, setUsername] = useState('');


  const handleRegister = () => {
    if (!email || !password || !name || !phoneNumber || (!username && role === 'moderator')) {
      setError('All fields are required.');
      return;
    }
    setError('');
  
    
    let registerUrl = '';
    switch (role) {
      case 'moderator':
        registerUrl = 'http://localhost:8080/moderator';
        break;
      case 'customer':
        registerUrl = 'http://localhost:8080/customer';
        break;
      case 'merchant':
        registerUrl = 'http://localhost:8080/merchant';
        break;
      default:
        setError('Invalid user role.');
        return;
    }
  
    fetch(registerUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email,
        password: password,
        name: name,
        phoneNumber: phoneNumber,
        username: username,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Registration failed.');
        }
        if (role === 'merchant') {
          navigate('/merchantdashboard');
        } else if(role === 'moderator') {
          navigate('/moderator')
        } else {
          navigate('/anasayfa')
        }
      })
      .catch((error) => {
        setError(error.message);
      });
  };
  

  const renderAdditionalInputs = () => {
    if (role === 'moderator' || role === 'customer') {
      return (
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      );
    
    }
    return null;
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="bg-gray-200 p-8 rounded-lg shadow-lg w-96">
        <h2 className="text-2xl font-bold mb-4 text-black text-center">Sign Up</h2>
        {error && <div className="bg-yellow-200 text-yellow-800 p-2 mb-4">{error}</div>}
        <select
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          value={role}
          onChange={(e) => setRole(e.target.value)}>
          <option value="">Select a role</option>
          <option value="customer">Customer</option>
          <option value="merchant">Merchant</option>
          <option value="moderator">Moderator</option>
        </select>
        {renderAdditionalInputs()}
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
       
        <input
          className="w-full px-4 py-2 mb-4 rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
          type="text"
          placeholder="Phone Number"
          value={phoneNumber}
          onChange={(e) => setPhoneNumber(e.target.value)}
        />
        <button
          className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-md mb-2"
          onClick={handleRegister}>
          Register
        </button>
      </div>
    </div>
  );
}

export default SignUp;
