import React, { useState, useEffect } from 'react';

function ManageAccount({ type }) {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            let url = '';
            if (type === 'merchant') {
                url = 'http://localhost:8080/merchant/1';
            } else if (type === 'moderator') {
                url = 'http://localhost:8080/moderator/1';
            } else if (type === 'customer') {
                url = 'http://localhost:8080/customer/1';
            } else {
                url = 'http://localhost:8080/customer/1';
            }

            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Failed to fetch ${type} details`);
            }
            const data = await response.json();
            setName(data.name);
            setEmail(data.email);
            setPassword(data.password);
            setPhoneNumber(data.phoneNumber);
        } catch (error) {
            console.error(`Error fetching ${type} details:`, error);
            setErrorMessage(`Failed to fetch ${type} details`);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let url = '';
            if (type === 'merchant') {
                url = 'http://localhost:8080/merchant/1';
            } else if (type === 'moderator') {
                url = 'http://localhost:8080/moderator/1';
            } else if (type === 'customer') {
                url = 'http://localhost:8080/customer/1';
            } else {
                url = 'http://localhost:8080/customer/1';
            }

            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    name,
                    email,
                    password,
                    phoneNumber,
                }),
            });
            if (!response.ok) {
                throw new Error(`Failed to update ${type} details`);
            }
            setSuccessMessage(`${type} details updated successfully`);
        } catch (error) {
            console.error(`Error updating ${type} details:`, error);
            setErrorMessage(`Failed to update ${type} details`);
        }
    };

    return (
        <div className="container mx-auto p-8">
            <h2 className="text-3xl font-bold mb-4">Manage Account</h2>
            <form onSubmit={handleSubmit} className="max-w-md">
                <div className="mb-4">
                    <label htmlFor="name" className="block text-sm font-medium text-gray-700">Name:</label>
                    <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} className="mt-1 p-2 border border-gray-300 rounded-md w-full" required />
                </div>
                <div className="mb-4">
                    <label htmlFor="email" className="block text-sm font-medium text-gray-700">Email:</label>
                    <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} className="mt-1 p-2 border border-gray-300 rounded-md w-full" required />
                </div>
                <div className="mb-4">
                    <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password:</label>
                    <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} className="mt-1 p-2 border border-gray-300 rounded-md w-full" required />
                </div>
                <div className="mb-4">
                    <label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700">Phone Number:</label>
                    <input type="text" id="phoneNumber" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} className="mt-1 p-2 border border-gray-300 rounded-md w-full" required />
                </div>
                <button type="submit" className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Update Account</button>
            </form>
            {successMessage && <div className="text-green-600 mt-4">{successMessage}</div>}
            {errorMessage && <div className="text-red-600 mt-4">{errorMessage}</div>}
        </div>
    );
}

export default ManageAccount;
