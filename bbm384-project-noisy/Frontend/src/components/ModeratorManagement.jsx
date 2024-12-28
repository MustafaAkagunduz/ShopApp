import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ModeratorManagement() {
    const [moderators, setModerators] = useState([]);
    const [newModerator, setNewModerator] = useState({
        name: '',
        username: '',
        email: '',
        password: '',
        gender: '',
        phoneNumber: '',
        profilePicture: ''
    });

    useEffect(() => {
        getAllModerators();
    }, []);

    const getAllModerators = async () => {
        try {
            const response = await axios.get('http://localhost:8080/moderator/all');
            setModerators(response.data);
        } catch (error) {
            console.error('Error fetching moderators:', error);
        }
    };

    const handleCreateModerator = async () => {
        try {
            await axios.post('http://localhost:8080/moderator', newModerator, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            setNewModerator({
                name: '',
                username: '',
                email: '',
                password: '',
                gender: '',
                phoneNumber: '',
                profilePicture: ''
            });
            getAllModerators(); // Refresh moderator list
        } catch (error) {
            console.error('Error creating moderator:', error);
        }
    };

    const handleDeleteModerator = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/moderator/${id}`);
            getAllModerators(); // Refresh moderator list
        } catch (error) {
            console.error('Error deleting moderator:', error);
        }
    };

    return (
        <div className="container mx-auto px-4 py-8">
            <h1 className="text-3xl font-semibold mb-4">Moderator Management</h1>
            
            {/* Form for creating new moderator */}
            <div className="flex flex-wrap mb-4">
                <input
                    type="text"
                    placeholder="Name"
                    value={newModerator.name}
                    onChange={(e) => setNewModerator({ ...newModerator, name: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <input
                    type="text"
                    placeholder="Username"
                    value={newModerator.username}
                    onChange={(e) => setNewModerator({ ...newModerator, username: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={newModerator.email}
                    onChange={(e) => setNewModerator({ ...newModerator, email: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={newModerator.password}
                    onChange={(e) => setNewModerator({ ...newModerator, password: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <input
                    type="text"
                    placeholder="Gender"
                    value={newModerator.gender}
                    onChange={(e) => setNewModerator({ ...newModerator, gender: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <input
                    type="text"
                    placeholder="Phone Number"
                    value={newModerator.phoneNumber}
                    onChange={(e) => setNewModerator({ ...newModerator, phoneNumber: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <input
                    type="text"
                    placeholder="Profile Picture"
                    value={newModerator.profilePicture}
                    onChange={(e) => setNewModerator({ ...newModerator, profilePicture: e.target.value })}
                    className="border border-gray-300 px-4 py-2 rounded mr-2 mb-2"
                />
                <button onClick={handleCreateModerator} className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">Create Moderator</button>
            </div>
            
            {/* List of moderators */}
            <ul>
                {moderators.map(moderator => (
                    <li key={moderator.id} className="flex justify-between items-center py-2 border-b">
                        <span>{moderator.name}</span>
                        <button onClick={() => handleDeleteModerator(moderator.id)} className="text-red-600 cursor-pointer">Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ModeratorManagement;
