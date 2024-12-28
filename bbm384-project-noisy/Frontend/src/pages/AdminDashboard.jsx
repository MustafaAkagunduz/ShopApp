import React, { useState , useEffect} from 'react';
import axios from 'axios';
import ModeratorManagement from '../components/ModeratorManagement';
import CategoryManagement from '../components/CategoryManagement';
import CommunityManagement from '../components/CommunityManagement';
import GenerateCoupon from '../components/GenerateCoupon';

function AdminDashboard() {
    const [selectedComponent, setSelectedComponent] = useState(null);

    
    return (
        <div className="flex h-[100vh] gap-96">
           <aside className="bg-gray-800 text-white w-64 flex-shrink-0">
              
            <nav className="p-4">
                <ul >
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('moderator_management')}>
                            Moderator Management
                        </button>
                    </li>
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('category_management')}>
                            Category Management
                        </button>
                    </li>
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('community_management')}>
                            Community Management
                        </button>
                    </li>
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('generate_coupon')}>
                            Generate Coupon
                        </button>
                    </li>
                </ul>
            </nav>
            </aside>

            <div className="selected-component col-span-2">
                {selectedComponent === 'moderator_management' && <ModeratorManagement />}
                {selectedComponent === 'category_management' && <CategoryManagement />}
                {selectedComponent === 'community_management' && <CommunityManagement />}
                {selectedComponent === 'generate_coupon' && <GenerateCoupon />}
            </div>
        </div>
    );
}

export default AdminDashboard;
