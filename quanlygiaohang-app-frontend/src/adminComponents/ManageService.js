import React, { useEffect, useState } from 'react';
import '../static/css/AdminDashboard.css';
import { Button, Card, Col, Form, Image, InputGroup, Row, Spinner, Table } from 'react-bootstrap';
import { authApi, endpoint } from '../configs/Apis';
import Pagination from '../layout/Pagination';
import { useNavigate } from 'react-router-dom';
import Switch from "react-switch";

const ManagerService = () => {
    const [active, setActive] = useState(true);
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [totalPages, setTotalPages] = useState(2);
    const [service, setService] = useState(null)
    const [loading, setLoading] = useState(false)
    const [serviceName, setServiceName] = useState(null)
    const [formData, setFormData] = useState({});

    const handleInputChange = (e, serviceId) => {
        setFormData({
            ...formData,
            [serviceId]: e.target.value,
        });
    };

    const handleSubmit = (serviceId) => {

        let form = new FormData()
        if (serviceId === 0) { form.append("serviceName", serviceName.trim()); setCurrentPage(1) }

        if (formData[serviceId] === undefined || formData[serviceId] === "")
            alert("Vui lòng nhập mệnh giá")
        else {
            form.append("id", serviceId)
            form.append("priceService", formData[serviceId])
            let res = authApi().post(endpoint['admin-addOrUpdate-service'], form)
            setLoading(true)
        }

    };



    const loadService = async (page) => {
        try {
            let form = new FormData()
            form.append("getActiveService", active)
            let url = `${endpoint['get-all-service']}?page=${page - 1}`
            const res = await authApi().post(url, form)
            console.info(res)
            setTotalPages(res.data.page.totalPages)
            setService(res.data._embedded.serviceList)
            setLoading(false)
        }
        catch (ex) { console.log(ex) }
        finally { }
    }

    const handlePageChange = (page) => {
        if (page < 1 || page > totalPages) return;
        setCurrentPage(page);

    };

    useEffect(() => {
        loadService(currentPage);
    }, [currentPage, loading, active]);


    if (service === null || loading === true)
        return <Spinner animation="border" />;

    return (
        <>

            <Form>
                <Form.Group className="mb-3 p-3" style={{ border: "solid 1px red", width: "40%" }}>
                    <Form.Label>Thêm dịch vụ</Form.Label>
                    <div className='d-flex'>

                        <Form.Control style={{ width: "300px", marginRight: "2px" }} type="text" placeholder="Tên dịch vụ"
                            onChange={(e) => setServiceName(e.target.value)}
                        />
                        <Form.Control style={{ width: "130px", marginRight: "2px" }} type="number" min="5000" placeholder="Nhập giá"
                            onChange={(e) => handleInputChange(e, 0)}
                            value={formData[0] || ""}
                        />
                        <Button variant="primary"
                            onClick={() => {

                                if ((serviceName.trim() === "" || serviceName === null)) alert("Vui lòng nhập tên dịch vụ")
                                else handleSubmit(0)
                                setActive(false)
                                setCurrentPage(1)
                            }}

                        >Thêm</Button></div>
                </Form.Group>


                <Button onClick={() => setActive(true)} variant="success" className='m-3'>Đã kích hoạt</Button>
                <Button onClick={() => setActive(false)} variant="warning">Chưa kích hoạt</Button>


            </Form>

            {service ?
                <Row className="w-100">{
                    service.map(c => (
                        <Col key={c.id} md={3} xs={12} className="mb-3">
                            <Card style={{ width: '18rem' }}>

                                <Card.Body>
                                    <h6>{c.name}</h6>
                                    <Card.Text>
                                        Mệnh giá: {c.price}
                                    </Card.Text>
                                    <Form>
                                        <Form.Group className="mb-3" >
                                            <Form.Label>Sửa giá</Form.Label>
                                            <div className='d-flex'>
                                                <Form.Control style={{ width: "150px", marginRight: "2px" }} type="number" min="5000" placeholder="Nhập giá"
                                                    onChange={(e) => handleInputChange(e, c.id)}
                                                    value={formData[c.id] || ""}
                                                />
                                                <Button variant="primary"
                                                    onClick={() => handleSubmit(c.id)}

                                                >Chỉnh giá</Button></div>
                                        </Form.Group>

                                    </Form>

                                    <label style={{ display: "flex", alignItems: "center" }}>

                                        <Switch
                                            onChange={() => {
                                                console.info(active)
                                                console.info(c.id)
                                                let form = new FormData()
                                                form.append("id", c.id)
                                                form.append("activeState", !active)
                                                let res = authApi().post(endpoint['admin-addOrUpdate-service'], form)
                                                setActive(!active)
                                                setCurrentPage(1)
                                                setLoading(true)
                                            }}
                                            checked={active}
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
                                        <span className='ml-2'>{active ? "Active" : "Inactive"}</span>
                                    </label>
                                </Card.Body>
                            </Card>
                        </Col>
                    )

                    )

                }

                </Row>
                :

                <></>}

            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />
        </>
    );
};

export default ManagerService;
