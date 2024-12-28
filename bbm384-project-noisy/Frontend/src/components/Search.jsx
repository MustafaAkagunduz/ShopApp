import React, { useState } from 'react';
import { FaSearch } from "react-icons/fa";

function Search({ onSearch }) {
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearchChange = (event) => {
    const query = event.target.value;
    setSearchQuery(query);
    onSearch(query); // Pass the search query to the parent component
  };

  return (
    <div className="relative">
      <input
        type="text"
        placeholder="Search"
        value={searchQuery}
        onChange={handleSearchChange}
        className="w-full border border-gray-300 rounded-lg py-2 px-4 pl-10 focus:outline-none focus:border-blue-500"
      />
      <FaSearch className="absolute top-3 left-3 text-gray-500"/>
    </div>
  );
}

export default Search;
