import React, { useState , useEffect} from 'react';
import axios from 'axios';
import ManageAccount from '../components/ManageAccount';
import SavedProducts from '../components/SavedProducts';
import PastProductRecommendations from '../components/PastProductRecommendations';
import ListRecommendation from '../components/ListRecommendation';
import CreateWishList from '../components/CreateWishList';

function UserDashboard() {
    const [userData, setUserData] = useState({});
    const [selectedComponent, setSelectedComponent] = useState(null);

    useEffect(() => {
        fetchUserData();
    }, []);

    const fetchUserData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/customer/1'); // 1 yerine dinamik olarak kullanıcı id'sini alabilirsiniz
            setUserData(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };
    return (
        <div className="flex h-[100vh]">
           <aside className="bg-gray-800 text-white w-64 flex-shrink-0">
                <div className="p-4 flex items-center">
                    {userData.profilePicture && (
                        <img src={`data:image/jpg;base64,${userData.profilePicture}`} alt="User Profile" className="userIcon w-10 h-10 rounded-full mr-2" />
                    )}
                    <p className="username text-lg font-semibold">{userData.name}</p>
                </div>

            <nav className="p-4">
                <ul >
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('manage_account')}>
                            Manage your account
                        </button>
                    </li>
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('past_product_recommendations')}>
                            Product recommendations
                        </button>
                    </li>
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('saved_products')}>
                            Saved Products
                        </button>
                    </li>
                    <li >
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('list_recommendation')}>
                            List the recommendations
                        </button>
                    </li>
                    <li>
                        <button className="block py-2 px-4 text-sm hover:underline underline-offset-4 rounded-full duration-300" onClick={() => setSelectedComponent('create_wishlist')}>
                            Create your wishlist
                        </button>
                    </li>
                </ul>
            </nav>
            </aside>

            <div className="selected-component col-span-2">
                {selectedComponent === 'manage_account' && <ManageAccount type="customer"/>}
                {selectedComponent === 'past_product_recommendations' && <PastProductRecommendations />}
                {selectedComponent === 'saved_products' && <SavedProducts />}
                {selectedComponent === 'list_recommendation' && <ListRecommendation />}
                {selectedComponent === 'create_wishlist' && <CreateWishList />}
            </div>
        </div>
    );
}

export default UserDashboard;
