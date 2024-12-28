import React from 'react';

function Header() {
  return (
    <header className="bg-gray-300 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">E-Commerce App</h1>
        <nav>
          <ul className="flex space-x-4">
            <li><a href="#" className="hover:text-gray-700">Home</a></li>
            <li><a href="#" className="hover:text-gray-700">Shop</a></li>
            <li><a href="#" className="hover:text-gray-700">About</a></li>
            <li><a href="#" className="hover:text-gray-700">Contact</a></li>
          </ul>
        </nav>
      </div>
    </header>
  );
}

export default Header;
