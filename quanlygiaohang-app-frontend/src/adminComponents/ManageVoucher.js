import React, { useEffect, useState } from 'react';
import '../static/css/AdminDashboard.css';
import { Button, Card, Col, Form, Image, InputGroup, Row, Spinner, Table } from 'react-bootstrap';
import { authApi, endpoint } from '../configs/Apis';
import Pagination from '../layout/Pagination';
import { useNavigate } from 'react-router-dom';


const ManagerVoucher = () => {
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [totalPages, setTotalPages] = useState(2);
    const [vouchers, setVoucher] = useState(null)
    const [loading, setLoading] = useState(false)
    const [cateName, setCateName] = useState(null)
    const [formData, setFormData] = useState({});

    const handleInputChange = (e, cateId) => {
        setFormData({
            ...formData,
            [cateId]: e.target.value,  // Cập nhật giá trị riêng cho mỗi category
        });
    };

    const handleSubmit = (cateId) => {
        
        let form = new FormData()
        if (cateId === 0) {form.append("cateName", cateName.trim()); setCurrentPage(1)}
     
        if (formData[cateId] === undefined || formData[cateId] === "")
            alert("Vui lòng nhập mệnh giá")
        else {
                form.append("id", cateId)
                form.append("priceCate", formData[cateId])
                let res = authApi().post(endpoint['admin-addOrUpdate-category'], form)
                setLoading(true)
            }
        
    };

    const loadVoucher = async (page) => {
        try {
            let url = `${endpoint['get-admin-voucher']}?page=${page-1}&isActive=true`
            const res = await authApi().get(url)
        console.info(res)
            setTotalPages(res.data.page.totalPages)
            setVoucher(res.data._embedded.voucherList)
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
        loadVoucher(currentPage);
    }, [currentPage, loading]);


    if (vouchers === null || loading === true)
        return <Spinner animation="border" />;

    return (
        <>
            <Form>
                <Form.Group className="mb-3 p-3" style={{ border: "solid 1px red", width: "40%" }}>
                    <Form.Label>Thêm voucher</Form.Label>
                    <div className='d-flex'>

                        <Form.Control style={{ width: "300px", marginRight: "2px" }} type="text" placeholder="Tên loại hàng"
                            onChange={(e) => setCateName(e.target.value)}
                        />
                        <Form.Control style={{ width: "130px", marginRight: "2px" }} type="number" min="5000" placeholder="Nhập giá"
                            onChange={(e) => handleInputChange(e, 0)}
                            value={formData[0] || ""}
                        />
                        <Button variant="primary"
                            onClick={() => {
                                if ((cateName.trim() === "" || cateName === null)) alert("Vui lòng nhập tên loại hàng")
                                else handleSubmit(0)}}

                        >Thêm</Button></div>
                </Form.Group>

            </Form>

            {vouchers ?
                <Row className="w-100">{
                    vouchers.map(c => (
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

                                    <Button variant="danger" onClick={()=>{
                                        let res = authApi().delete(endpoint['admin-delete-category'](c.id))
                                        setLoading(true)
                                    }}>Xóa</Button>
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

export default ManagerVoucher;
