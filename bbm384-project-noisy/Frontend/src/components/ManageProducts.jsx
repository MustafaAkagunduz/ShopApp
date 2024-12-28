import React, { useState, useEffect } from 'react';

function ManageProducts() {
    const [products, setProducts] = useState([]);
    const [pageInfo, setPageInfo] = useState({
        pageNo: 0,
        pageSize: 10,
        totalElements: 0,
        totalPages: 0,
        last: true
    });
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredProducts, setFilteredProducts] = useState([]);

    useEffect(() => {
        fetchProducts(pageInfo.pageNo);
    }, [pageInfo.pageNo]); // Fetch products whenever pageNo changes

    useEffect(() => {
        setFilteredProducts(products.filter(product =>
            product.name.toLowerCase().includes(searchTerm.toLowerCase())
        ));
    }, [searchTerm, products]); // Filter products whenever searchTerm or products change

    const fetchProducts = async (pageNumber) => {
        try {
            const response = await fetch(`http://localhost:8080/product/all/merchant/{merchantName}?page=${pageNumber}&size=${pageInfo.pageSize}&merchantName=string`);
            if (!response.ok) {
                throw new Error('Failed to fetch products');
            }
            const data = await response.json();
            // Decode base64 image data for each product
            const decodedProducts = data.content.map(product => ({
                ...product,
                image: product.image
            }));
            setProducts(decodedProducts);
            setPageInfo({
                pageNo: data.pageNo,
                pageSize: data.pageSize,
                totalElements: data.totalElements,
                totalPages: data.totalPages,
                last: data.last
            });
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handlePreviousPage = () => {
        if (pageInfo.pageNo > 0) {
            setPageInfo(prevPageInfo => ({
                ...prevPageInfo,
                pageNo: prevPageInfo.pageNo - 1
            }));
        }
    };

    const handleNextPage = () => {
        if (!pageInfo.last) {
            setPageInfo(prevPageInfo => ({
                ...prevPageInfo,
                pageNo: prevPageInfo.pageNo + 1
            }));
        }
    };

    return (
        <div className="container mx-auto p-8">
            <h2 className="text-3xl font-bold mb-4">Manage Products</h2>
            <div className="mb-4">
                <input type="text" value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} placeholder="Search products by name" className="px-4 py-2 border border-gray-300 rounded-md mr-2" />
            </div>
            <div className="grid grid-cols-2 gap-4">
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
                            <img src={`data:image/jpg;base64,${product.image}`} alt={product.name} className="mt-2 w-full max-h-40 object-contain" />
                        )}
                        </div>
                    </div>
                ))}
            </div>
            <div className="mt-4 flex justify-center gap-8">
                <button onClick={handlePreviousPage} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600" disabled={pageInfo.pageNo === 0}>Previous</button>
                <button onClick={handleNextPage} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 mx-2" disabled={pageInfo.last}>Next</button>
            </div>
        </div>
    );
}

export default ManageProducts;
