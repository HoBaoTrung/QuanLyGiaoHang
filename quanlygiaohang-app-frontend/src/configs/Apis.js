import axios from "axios";
import cookies from 'react-cookies'

const SERVER_CONTEXT = '/QuanLyGiaoHang';
const SERVER = 'http://localhost:8080'
export const endpoint = {

    'login': `${SERVER_CONTEXT}/api/auth/login/`,
    'register': `${SERVER_CONTEXT}/api/auth/register/`,
    'current-user': `${SERVER_CONTEXT}/api/current-user/`,
    'get-shipper': (shipperID) => `${SERVER_CONTEXT}/api/customer/getShipper/${shipperID}`,

    'get-all-service': `${SERVER_CONTEXT}/api/public/getAllService/`,
    'get-all-cate': `${SERVER_CONTEXT}/api/public/getAllCate/`,
    'get-provinces': (locationName) => `${SERVER_CONTEXT}/api/public/getProvince/${locationName}/`,
    'get-districts': (provinceID) => `${SERVER_CONTEXT}/api/public/getDistricts/${provinceID}/`,

    'get-orderDetail-for-shipper': (productId) => `${SERVER_CONTEXT}/api/shipper/getOrder/${productId}/`,
    'add-or-update-daugia': `${SERVER_CONTEXT}/api/shipper/addDauGia/`,

    'add-order': `${SERVER_CONTEXT}/api/customer/addOrder/`,
    'get-orders': `${SERVER_CONTEXT}/api/customer/orders/`,
    'get-order-detail': (productId) => `${SERVER_CONTEXT}/api/customer/getOrder/${productId}/`,
    'pay-return': `${SERVER_CONTEXT}/api/customer/vnpay-return/`,
    'pay': `${SERVER_CONTEXT}/api/customer/pay/`,
    'add-shipper-proof': (productId) => `${SERVER_CONTEXT}/api/shipper/addProof/${productId}`,

    'get-owner-voucher': `${SERVER_CONTEXT}/api/customer/vouchers/`,
    'get-comments': (shipperId) => `${SERVER_CONTEXT}/api/customer/getComments/${shipperId}`,
    'add-comments': `${SERVER_CONTEXT}/api/customer/addComments/`,
    'get-user-point': `${SERVER_CONTEXT}/api/customer/getRate/`,
    'add-user-point': `${SERVER_CONTEXT}/api/customer/postRate/`,
    'get-average-point': (shipperId) => `${SERVER_CONTEXT}/api/public/getRateOfShipper/${shipperId}`,

    'get-firebase-token': `${SERVER_CONTEXT}/api/customer/firebase-custom-token`,

    'get-admin-user': `${SERVER_CONTEXT}/api/admin/getAllUsers/`,
    'get-admin-shipper': `${SERVER_CONTEXT}/api/admin/getAllShipper/`,
    'put-admin-active-shipper': (shipperId) => `${SERVER_CONTEXT}/api/admin/${shipperId}/toggle`,
    'admin-order': `${SERVER_CONTEXT}/api/admin/allOrders/`,
    'admin-addOrUpdate-category': `${SERVER_CONTEXT}/api/admin/addOrUpdateCategory/`,
    'admin-delete-category': (cateId) => `${SERVER_CONTEXT}/api/admin/deleteCategory/${cateId}`,
    'admin-addOrUpdate-service': `${SERVER_CONTEXT}/api/admin/addOrUpdateService/`,
    'admin-statistic-price': `${SERVER_CONTEXT}/api/admin/price-by-period/`,
    'admin-statistic-quantity': `${SERVER_CONTEXT}/api/admin/count-by-period/`,
    'admin-statistic-service': `${SERVER_CONTEXT}/api/admin/count-by-service/`
}
export const authApi = () => {
    return axios.create({
        baseURL: SERVER,
        headers: {
            'Authorization': `Bearer ${cookies.load('token')}`

        }
    })
}

export default axios.create({
    baseURL: SERVER

})