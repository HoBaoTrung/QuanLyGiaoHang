import React, {  useEffect,  useState } from 'react';
import '../static/css/ProfileCard.css';
import '../static/css/StarRating.css';
import Switch from "react-switch";
import {Card, Col, Image, Row, Spinner } from 'react-bootstrap';

import Apis, { authApi, endpoint } from '../configs/Apis';
import { useParams } from 'react-router-dom';
import Comment from '../common/Comment';
import ShipperInfo from '../common/ShipperInfo';

const ManagerShipperDetail = () => {
    const [averageReview, setAverageReview] = useState(null);
    const { shipperId } = useParams()
    const [isActive, setIsActive] = useState(false);
    const [user, setUser] = useState(null);

    const loadData = async () => {

        let res;
        try {

            res = await Apis.get(endpoint['get-average-point'](shipperId))
            setAverageReview(res.data)

            res = await authApi().get(endpoint['get-shipper'](shipperId))
            console.log(res)
            setUser(res.data)
            setIsActive(res.data.active)


        }
        catch (e) { }

    };

    const handleToggle = () => {
        const newActiveState = !isActive;
        setIsActive(newActiveState);

        try {

            let url = endpoint['put-admin-active-shipper'](shipperId)

            let res = authApi().put(url, { active: newActiveState })


        } catch (error) {
        }
    };

    useEffect(() => {
        loadData();

    }, []);
    // comments === null || 
    if (user === null || averageReview === null)
        return <Spinner animation="border" />;

    return (
        <Row className="mt-5">

            <Col md={6}>
                <Card className="profile-card m-3" style={{ height: "30rem" }}>
                    <Card.Body className="d-flex align-items-start">
                        <div>
                            <Image
                                src={user.avatar} // URL ảnh đại diện hoặc thay bằng ảnh thật
                                roundedCircle
                                className="profile-image mb-3"
                            />

                            <label style={{ display: "flex", alignItems: "center" }}>
                               
                                <Switch
                                    onChange={handleToggle}
                                    checked={isActive}
                                    onColor="#86d3ff"
                                    onHandleColor="#2693e6"
                                    handleDiameter={30}
                                    uncheckedIcon={false}
                                    checkedIcon={false}
                                    boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
                                    activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
                                    height={20}
                                    width={50}
                                />
                                 <span className='ml-2'>{isActive ? "Active" : "Inactive"}</span>
                            </label>
                        </div>

                        <div className="ml-3">
                            
                            <ShipperInfo shipperId={user.id} />
                        </div>

                    </Card.Body>

                </Card>
            </Col>
            {user !== null ?

                < Col md={6}>
                    <Comment shipperId={user.id} />

                </Col>
                : <></>}

        </Row >
    );
}
export default ManagerShipperDetail;