import React from 'react';

function ProductCard({ title, price, imageUrl }) {
  return (
    <div className="bg-white p-4 rounded shadow">
      <img src={imageUrl} alt={title} className="w-full mb-2" />
      <h2 className="text-lg font-semibold">{title}</h2>
      <p className="text-gray-600">{price}</p>
    </div>
  );
}

export default ProductCard;
