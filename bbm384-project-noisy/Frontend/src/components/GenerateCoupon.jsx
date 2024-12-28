import React, { useState } from 'react';
import axios from 'axios';

function GenerateCoupon() {
    const [formData, setFormData] = useState({
        numberOfCoupons: '',
        discountAmount: '',
        expiryDate: ''
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const expiryDateISO = new Date(formData.expiryDate).toISOString(); // Tarihi ISO 8601 formatına çevirme
            const updatedFormData = { ...formData, expiryDate: expiryDateISO };
            
            const response = await axios.post('http://localhost:8080/coupon', updatedFormData);
            console.log('Coupons created:', response.data);
            // Burada isteğe bağlı olarak kullanıcıya bir bildirim gösterebilirsiniz
        } catch (error) {
            console.error('Error creating coupons:', error);
            // Burada bir hata durumunda kullanıcıya uygun bir mesaj gösterebilirsiniz
        }
    };

    return (
        <div className="flex justify-center items-center h-screen">
            <div className="bg-gray-200 p-8 rounded-lg">
                <h2 className="text-xl font-semibold mb-4">Generate Coupons</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block mb-2" htmlFor="numberOfCoupons">Number of Coupons:</label>
                        <input
                            type="number"
                            id="numberOfCoupons"
                            name="numberOfCoupons"
                            value={formData.numberOfCoupons}
                            onChange={handleChange}
                            className="border border-gray-300 rounded px-4 py-2 w-full"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block mb-2" htmlFor="discountAmount">Discount Amount:</label>
                        <input
                            type="number"
                            id="discountAmount"
                            name="discountAmount"
                            value={formData.discountAmount}
                            onChange={handleChange}
                            className="border border-gray-300 rounded px-4 py-2 w-full"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block mb-2" htmlFor="expiryDate">Expiry Date:</label>
                        <input
                            type="datetime-local" // datetime-local input tipini kullanarak tarih ve saat girişi sağlarız
                            id="expiryDate"
                            name="expiryDate"
                            value={formData.expiryDate}
                            onChange={handleChange}
                            className="border border-gray-300 rounded px-4 py-2 w-full"
                            required
                        />
                    </div>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">Generate</button>
                </form>
            </div>
        </div>
    );
}

export default GenerateCoupon;
