import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Search from "../components/Search";

function Main() {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [categoryFilter, setCategoryFilter] = useState("");
  const [priceFilter, setPriceFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(0); // Sayfa numarasını 0'dan başlat
  const [totalPages, setTotalPages] = useState(1); // Toplam sayfa sayısını 1'den başlat
  const [productsPerPage] = useState(10);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchProducts();
  }, [currentPage]); // currentPage değiştiğinde fetchProducts'u çağır

  useEffect(() => {
    filterProducts(searchQuery, categoryFilter, priceFilter);
  }, [searchQuery, categoryFilter, priceFilter, products]); // products state'i değiştiğinde filterProducts'u çağır

  const fetchProducts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/product/all/pages?page=${currentPage}&size=${productsPerPage}`);
      setProducts(response.data.content);
      setFilteredProducts(response.data.content);
      setTotalPages(response.data.totalPages); // Toplam sayfa sayısını ayarla
      setLoading(false);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const handleSearch = (query) => {
    setSearchQuery(query);
  };

  const handleCategoryFilterChange = (category) => {
    setCategoryFilter(category);
  };

  const handlePriceFilterChange = (price) => {
    setPriceFilter(price);
  };

  const filterProducts = (searchQuery, category, price) => {
    let filtered = products;

    if (searchQuery) {
      filtered = filtered.filter(product => product.name.toLowerCase().includes(searchQuery.toLowerCase()));
    }

    if (category) {
      filtered = filtered.filter(product => product.categoryName.toLowerCase() === category.toLowerCase());
    }

    if (price) {
      const [minPrice, maxPrice] = price.split('-');
      filtered = filtered.filter(product => product.price >= minPrice && product.price <= maxPrice);
    }

    setFilteredProducts(filtered);
  };

  const handlePrevPage = () => {
    if (currentPage > 0) { // Sayfa numarası 0'dan büyükse önceki sayfaya git
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) { // Sayfa numarası toplam sayfa sayısından bir az ise sonraki sayfaya git
      setCurrentPage(currentPage + 1);
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-2xl mb-4">ShopApp</h1>
      <Search onSearch={handleSearch} />
      {/* Filtreleme seçenekleri */}
      <div className="flex mb-4">
        {/* Kategori filtresi */}
        <select onChange={(e) => handleCategoryFilterChange(e.target.value)} className="mr-4 p-2 border border-gray-300 rounded-md">
          <option value="">All Categories</option>
          {/* Buraya kategori seçeneklerini dinamik olarak ekle */}
          <option value="deneme">Deneme</option>
        </select>
        {/* Fiyat filtresi */}
        <select onChange={(e) => handlePriceFilterChange(e.target.value)} className="p-2 border border-gray-300 rounded-md">
          <option value="">All Prices</option>
          <option value="0-20">$0 - $20</option>
          <option value="21-50">$21 - $50</option>
          <option value="51-100">$51 - $100</option>
          {/* Dilerseniz fiyat aralıklarını isteğinize göre daha detaylı hale getirebilirsiniz */}
        </select>
      </div>
      {/* Ürünlerin listesi */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {filteredProducts.map(product => (
                    <div key={product.id} className="border border-gray-300 rounded-md p-4">
                        <div className='flex flex-row '>
                            <div>
                        <h3 className="text-xl font-bold">{product.name}</h3>
                        <p className="text-gray-700">{product.description}</p>
                        <p className="text-gray-700">Price: {product.price} TL</p>
                        <p className="text-gray-700">Category: {product.categoryName}</p>
                        <p className="text-gray-700">Stock: {product.stock}</p>
                        <p className="text-gray-700">Available: {product.available ? 'Yes' : 'No'}</p>
                        </div>
                        {product.image && (
                            <img src={`data:image/jpg;base64,${product.image}`} alt={product.name} className="mt-2 w-full max-h-48 max-w-48 object-contain" />
                        )}
                        </div>
                    </div>
                ))}
      </div>
      {/* Sayfalama düğmeleri */}
      <div className="flex justify-center mt-8">
        <button
          onClick={handlePrevPage}
          className={`mx-1 px-3 py-1 rounded-md focus:outline-none ${currentPage === 0 ? 'bg-gray-300 cursor-not-allowed' : 'bg-blue-500 text-white'}`}
          disabled={currentPage === 0}
        >
          Prev
        </button>
        {/* Sayfa numaraları */}
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i}
            onClick={() => setCurrentPage(i)}
            className={`mx-1 px-3 py-1 rounded-md focus:outline-none ${currentPage === i ? 'bg-blue-500 text-white' : 'bg-gray-300'}`}
          >
            {i + 1}
          </button>
        ))}
        <button
          onClick={handleNextPage}
          className={`mx-1 px-3 py-1 rounded-md focus:outline-none ${currentPage === totalPages - 1 ? 'bg-gray-300 cursor-not-allowed' : 'bg-blue-500 text-white'}`}
          disabled={currentPage === totalPages - 1}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default Main;
