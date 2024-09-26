import { useEffect, useState } from "react";
import Apis, { authApi, endpoint } from "../configs/Apis";
import { Spinner } from "react-bootstrap";
const ShipperInfo = ({ shipperId }) => {
    const [averageReview, setAverageReview] = useState(0);
    const [user, setUser] = useState(null);
    const loadShipper = async () => {

        let res;
        try {
            res = await Apis.get(endpoint['get-average-point'](shipperId))
            setAverageReview(res.data)
            res = await authApi().get(endpoint['get-shipper'](shipperId))
            setUser(res.data)
            console.info(res.data)

        }
        catch (e) { }

    };
    useEffect(() => {

        loadShipper();
    }, []);

    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'decimal',
        minimumFractionDigits: 0, // Không cần số thập phân
        maximumFractionDigits: 0  // Không cần số thập phân
    });

    if (user === null)
        return <Spinner animation="border" />;
    return (
        <>
            <h4 className="profile-name">{user.username}</h4>
            <p><strong>Email:</strong>{user.email}</p>
            <p><strong>Số điện thoại:</strong> {user.phone}</p>
            <p><strong>Vai trò:</strong> SHIPPER </p>
            <p><strong>CMND/CCCD:</strong> {user.cmnd} </p>
            <p><strong>Đánh giá:</strong> {averageReview} ★</p>
            <p><strong>Số đơn đã nhận:</strong> {user.totalOrder} đơn</p>
            <p><strong>Tổng doanh thu:</strong> {formatter.format(user.totalPrice)} vnđ</p>
        </>
    )
}
export default ShipperInfo