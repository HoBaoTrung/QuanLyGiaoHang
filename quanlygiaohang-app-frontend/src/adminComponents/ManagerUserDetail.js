import React, { useEffect, useState } from 'react';
import '../static/css/ProfileCard.css';
import '../static/css/StarRating.css';
import Switch from "react-switch";
import { Button, Card, Col, Image, Row, Spinner } from 'react-bootstrap';

import Apis, { authApi, endpoint } from '../configs/Apis';
import { useNavigate, useParams } from 'react-router-dom';


const ManagerUserDetail = () => {
    const { userId } = useParams()
    const [isActive, setIsActive] = useState(false);
    const [user, setUser] = useState(null);
    const nav = useNavigate()
    const loadData = async () => {

        let res;
        try {
            res = await authApi().get(endpoint['get-userDetail'](userId))
            setUser(res.data)
            setIsActive(res.data.active)
        }
        catch (e) { }

    };


    useEffect(() => {
        loadData();

    }, []);
    // comments === null || 
    if (user === null)
        return <Spinner animation="border" />;

    return (
        <Row className="mt-5">

            <Col md={9}>
                <Card className="profile-card m-3" style={{ height: "30rem" }}>
                    <Card.Body className="d-flex align-items-start">
                        <div>
                            <Image
                                src={user.avatar} // URL ảnh đại diện hoặc thay bằng ảnh thật
                                roundedCircle
                                className="profile-image mb-3"
                            />


                        </div>

                        <div className="ml-3">
                            <h4 className="profile-name">{user.username}</h4>
                            <p><strong>Email:</strong>{user.email}</p>
                            <p><strong>Số điện thoại:</strong> {user.phone}</p>
                        </div>

                    </Card.Body>
                    <Button variant='danger' onClick={() => {
                        authApi().delete(endpoint['delete-user'](user.id))
                        nav("/admin/customers")
                    }}>Xóa</Button>
                </Card>
            </Col>
        </Row >
    );
}
export default ManagerUserDetail;