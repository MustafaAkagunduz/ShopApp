import React, { useState } from 'react';
import { FaUser } from 'react-icons/fa';
import ManageAccount from '../components/ManageAccount';
import ReviewFeedbacks from '../components/ReviewFeedbacks';
import ManageProducts from '../components/ManageProducts';
import AddProducts from '../components/AddProducts';

function MerchantDashboard() {
    const [selectedComponent, setSelectedComponent] = useState(null);

    const handleLogout = () => {
        // Clear the bearer token cookie (replace 'token' with your actual cookie name)
        document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        window.location.href = '/';
        
    };

    return (
        <div className="flex h-[100vh]">
            {/* Sidebar */}
            <aside className="bg-gray-800 text-white w-64 flex-shrink-0">
                <div className="p-4 flex items-center">
                    <FaUser className="userIcon text-4xl mr-2" />
                    <p className="username text-lg font-semibold">John Doe</p>
                </div>
                <nav className="p-4">
                    <ul>
                        <li>
                            <button onClick={() => setSelectedComponent('manage_account')} className="block py-2 px-4 text-sm">Manage Account</button>
                        </li>
                        <li>
                            <button onClick={() => setSelectedComponent('review_feedbacks')} className="block py-2 px-4 text-sm">Review Feedbacks</button>
                        </li>
                        <li>
                            <button onClick={() => setSelectedComponent('manage_products')} className="block py-2 px-4 text-sm">Manage Products</button>
                        </li>
                        <li>
                            <button onClick={() => setSelectedComponent('add_products')} className="block py-2 px-4 text-sm">Add Products</button>
                        </li>
                        {/* Log out button */}
                        <li className='mt-96'>
                            <button onClick={handleLogout} className="block py-2 px-4 text-sm">Log out</button>
                        </li>
                    </ul>
                </nav>
            </aside>

            {/* Content */}
            <main className="flex-1 p-8 overflow-y-auto">
                <h1 className="text-2xl font-bold mb-4">Merchant Dashboard</h1>
                {selectedComponent === 'manage_account' && <ManageAccount type="merchant" />}
                {selectedComponent === 'review_feedbacks' && <ReviewFeedbacks />}
                {selectedComponent === 'manage_products' && <ManageProducts />}
                {selectedComponent === 'add_products' && <AddProducts />}
            </main>
        </div>
    );
}

export default MerchantDashboard;
