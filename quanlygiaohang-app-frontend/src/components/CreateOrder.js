import { Button, Col, Container, Form, Row } from "react-bootstrap";
import '../static/css/CreateOrder.css';
import React, { useCallback, useEffect, useRef, useState } from "react";
import Modal from 'react-modal';
import Apis, { authApi, endpoint } from "../configs/Apis";
import { useParams, useNavigate } from "react-router-dom";
import MySpinner from "../layout/Spinner";
import * as Validator from '../configs/Validator';
import UploadImage from "../common/UploadImage";
import FormSelectProvince from "../common/FormSelectProvince";
Modal.setAppElement('#root');
const CreateOrder = () => {
    const img = useRef(null)
    const { locationName } = useParams()
    const [avatar, setAvatar] = useState(null);
    const [error, setError] = useState('');
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [selectedOptions, setSelectedOptions] = useState([]);
    const [inputValue, setInputValue] = useState('');

    const [service, setService] = useState(null);
    const [category, setCate] = useState([]);

    const [fromProvinces, setFromProvinces] = useState([]);
    const [toProvinces, setToProvinces] = useState([]);

    const [fromDistricts, setFromDistrict] = useState([])
    const [toDistricts, setToDistrict] = useState([]);

    //dữ liệu gửi lên server
    const [order, setOrder] = useState({
        "senderFullname": "",
        "senderPhone": "",
        'senderAddress': '',
        'senderDistrict': "",

        "receiverFullname": "",
        "receiverPhone": "",
        'receiverAddress': '',
        'receiverDistrict': "",

        productName: "",
        quantity: "",
        service: "",
        category: "",
        note: ""
    })


    const handleImageChange = (image) => {

        img.current = image;

    };
    const loadService = async () => {
        let form = new FormData()
        if (!window.location.href.includes('/admin/')) {
            form.append("getActiveService", true)
        }
        try {
            const res = await Apis.post(endpoint['get-all-service'], form);

            setService(res.data._embedded.serviceList)
        }
        catch (ex) { console.error(ex) }
    };

    const loadCate = async () => {

        try {
            const res = await Apis.get(endpoint['get-all-cate']);

            setCate(res.data._embedded.categoryList)

        }
        catch (ex) { console.error(ex) }
    };


    const loadProvinces = async () => {

        try {

            const res = await Apis.get(endpoint['get-provinces'](locationName));
            const provincesData = res.data;
            setFromProvinces(provincesData)
            setToProvinces(provincesData)

        }
        catch (ex) { console.error(ex) }


    };


    const toggleOption = (option) => {
        setSelectedOptions((prev) =>
            prev.includes(option)
                ? prev.filter((item) => item !== option)
                : [...prev, option]
        );
    };

    const handleConfirm = () => {
        setInputValue(selectedOptions.join(', '));
        setModalIsOpen(false);
        const selectedItems = category.filter((item) =>
            selectedOptions.includes(item.name)
        );
        const ids = selectedItems.map(obj => obj.id);

        const newArray = [...ids];
        setOrder(current => {
            return { ...current, category: newArray }
        })

    };

    const handleClose = () => {
        setSelectedOptions(inputValue.split(', ').filter((item) => item));
        setModalIsOpen(false);
    };



    const selectProvinces = (value, selectName) => {

        if (selectName === 'from') {

            loadFromDistricts(value)
        } else if (selectName === 'to') {

            loadToDistricts(value)
        }
    };

    const loadFromDistricts = async (provinceID) => {

        try {

            const response = await Apis.get(endpoint['get-districts'](provinceID));
            setFromDistrict(response.data);
            setOrder(prevOrder => ({
                ...prevOrder,
                senderDistrict: ""
            }));
        }
        catch (ex) { console.error(ex) }
    }

    const loadToDistricts = async (provinceID) => {

        try {

            const response = await Apis.get(endpoint['get-districts'](provinceID));
            setToDistrict(response.data);
            setOrder(prevOrder => ({
                ...prevOrder,
                receiverDistrict: ""
            }));
        }
        catch (ex) { console.error(ex) }
    }

    useEffect(() => {
        const fetchData = async () => {
            await Promise.all([
                loadService(), loadCate()
            ]);

        };
        fetchData();

    }, [])

    useEffect(() => {
        if (service)
            setOrder(prevOrder => ({
                ...prevOrder,
                service: service[0].id
            }));

    }, [service]);

    useEffect(() => {
        loadProvinces();

    }, [locationName]); // Load provinces khi locationName thay đổi

    const changeField = (e, field) => {
        setOrder(current => {
            return { ...current, [field]: e.target.value }
        })

    }
    const nav = useNavigate()
    const submitOrder = (event) => {
        event.preventDefault();

        const process = async () => {

            let form = new FormData()
            for (let i in order)
                form.append(i, order[i])
            form.append('image', img.current)


            try {

                const res = await authApi().post(endpoint['add-order'], form);
                if (res.status === 201) {
                    nav("/orders");
                }

            }
            catch (error) { }
        }
        if (img.current === null)
            alert("Vui lòng chọn ảnh sản phẩm")
        else process()


    }




    return (
        <Container>

            <Form onSubmit={submitOrder}>
                <Row>
                    <Col>

                        <div class="ticket">
                            <div class="ticket-header">THÔNG TIN NGƯỜI GỬI</div>

                            <div class="ticket-field">
                                <label>Họ tên:</label>
                                <Form.Control type="text" placeholder="Nhập họ tên" required='true'
                                    value={order.senderFullname} onChange={e => changeField(e, "senderFullname")} />
                            </div>

                            <div class="ticket-field">
                                <label>Số điện thoại:</label>
                                <Form.Control type="tel" placeholder="Nhập số điện thoại" required='true'
                                    pattern="[0-9]{4}[0-9]{3}[0-9]{3}" title="Vui lòng nhập đúng định dạng số điện thoại"
                                    value={order.senderPhone} onChange={e => changeField(e, "senderPhone")} />

                            </div>

                            <div class="ticket-field">
                                <label>Địa chỉ:</label>
                                <Form.Control type="text" placeholder="Nhập địa chỉ người giao" required='true'
                                    value={order.senderAddress} onChange={e => changeField(e, "senderAddress")} />
                            </div>


                            <FormSelectProvince locationName={locationName} disabled={false} selectedProvince={(data) => selectProvinces(data, 'from')} />
                            <div class="ticket-field">
                                <label>Quận/Huyện:</label>
                                <Form.Select onChange={e => changeField(e, "senderDistrict")} className="custom-select" aria-label="Default select example"
                                >
                                    <option>Chọn Quận/Huyện</option>
                                    {fromDistricts ? <>
                                        {fromDistricts.map(p =>
                                            <option selected key={p.id} value={p.id}>{p.districtName}</option>
                                        )}
                                    </> : <MySpinner />

                                    }
                                </Form.Select>
                            </div>

                        </div>
                    </Col>


                    <Col>
                        <div class="ticket">
                            <div class="ticket-header">THÔNG TIN NGƯỜI NHẬN</div>

                            <div class="ticket-field">
                                <label>Họ tên:</label>
                                <Form.Control type="text" placeholder="Nhập họ tên" required='true'
                                    value={order.receiverFullname} onChange={e => changeField(e, "receiverFullname")} />
                            </div>

                            <div class="ticket-field">
                                <label>Số điện thoại:</label>
                                <Form.Control type="tel" placeholder="Nhập số điện thoại" required='true'
                                    pattern="[0-9]{4}[0-9]{3}[0-9]{3}" title="Vui lòng nhập đúng định dạng số điện thoại"
                                    value={order.receiverPhone} onChange={e => changeField(e, "receiverPhone")} />

                            </div>

                            <div class="ticket-field">
                                <label>Địa chỉ:</label>
                                <Form.Control type="text" placeholder="Nhập địa chỉ người giao" required='true'
                                    value={order.receiverAddress} onChange={e => changeField(e, "receiverAddress")} />
                            </div>

                            <div class="ticket-field">
                                <FormSelectProvince locationName={locationName} disabled={true} selectedProvince={(data) => selectProvinces(data, 'to')} />

                            </div>

                            <div class="ticket-field">
                                <label>Quận/Huyện:</label>
                                {toDistricts && (
                                    <Form.Select onChange={e => changeField(e, "receiverDistrict")} className="custom-select" aria-label="Default select example"
                                    >
                                        <option>Chọn Quận/Huyện</option>

                                        {(locationName ? fromDistricts : toDistricts) ? <>
                                            {(locationName ? fromDistricts : toDistricts).map(p =>
                                                <option key={p.id} value={p.id}>{p.districtName}</option>
                                            )}
                                        </> : <MySpinner />

                                        }
                                    </Form.Select>
                                )}

                            </div>

                        </div>


                    </Col>
                </Row>

                <div class="parents mt-3">
                    <div class="ticket">
                        <div class="ticket-header">THÔNG TIN MÓN HÀNG</div>
                        <Row>
                            <Col>

                                <div class="ticket-field">
                                    <label>Tên sản phẩm:</label>
                                    <Form.Control type="text" placeholder="Nhập tên sản phẩm" required='true'
                                        value={order.productName} onChange={e => changeField(e, "productName")} />
                                </div>

                                <div class="ticket-field">
                                    <label>Số lượng:</label>
                                    <Form.Control type="number" placeholder="Nhập số lượng" required='true'
                                        value={order.quantity} onChange={e => changeField(e, "quantity")} />
                                </div>

                                <div class="ticket-field">
                                    <label>Chọn loại hàng:</label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Chọn loại hàng"
                                        value={inputValue}
                                        readOnly
                                        onClick={() => setModalIsOpen(true)}
                                        style={{ cursor: 'pointer' }}
                                    />

                                    <Modal
                                        isOpen={modalIsOpen}
                                        onRequestClose={handleClose}

                                        style={{
                                            content: {
                                                width: '600px',
                                                margin: 'auto',
                                                borderRadius: '10px',
                                                padding: '20px',
                                                boxShadow: '0px 0px 15px rgba(0, 0, 0, 0.1)',
                                            },
                                            overlay: {
                                                backgroundColor: 'rgba(0, 0, 0, 0.5)',
                                            },
                                        }}
                                    >

                                        <div
                                            style={{
                                                display: 'grid',
                                                gridTemplateColumns: '1fr 1fr',
                                                gap: '10px',
                                            }}
                                        >
                                            {category.map((option) => (
                                                <label key={option.name}>
                                                    <input
                                                        type="checkbox"
                                                        checked={selectedOptions.includes(option.name)}
                                                        onChange={() => toggleOption(option.name)}
                                                    />
                                                    {option.name}
                                                </label>
                                            ))}
                                        </div>
                                        <div style={{
                                            position: 'absolute',
                                            bottom: '20px', // Đặt khoảng cách từ dưới cùng của modal
                                            right: '20px', // Đặt khoảng cách từ cạnh phải của modal
                                            display: 'flex',
                                            gap: '10px', // Khoảng cách giữa hai nút
                                        }}>
                                            <button onClick={handleClose} style={{ marginRight: '10px' }}>
                                                ĐÓNG
                                            </button>
                                            <button onClick={handleConfirm} style={{ color: 'red' }}>
                                                XÁC NHẬN
                                            </button>
                                        </div>
                                    </Modal>
                                </div>

                            </Col>
                            <Col>
                                <div class="ticket-field">
                                    <label>Chọn dịch vụ:</label>
                                    {service !== null ?
                                        service.map((s, index) => <>


                                            <Form.Check
                                                key={index}
                                                type="radio"
                                                label={s.name}
                                                id={`radio-${index}`}
                                                name="serviceRadioGroup"
                                                value={s.id} // Giữ giá trị cho radio
                                                checked={+order.service === s.id}
                                                onChange={e => changeField(e, "service")}

                                            /></>
                                        ) : <></>}
                                </div>
                                <div class="ticket-field">
                                    <label>Ghi chú:</label>
                                    <Form.Control type="aria" placeholder="Nhập ghi chú"
                                        value={order.note} onChange={e => changeField(e, "note")} />
                                </div>

                            </Col>
                            <Col>
                                <UploadImage onImageChange={handleImageChange} />
                            </Col>

                        </Row>
                    </div>
                </div>

                <Button className="mt-3" variant="primary" type="submit">
                    Submit
                </Button>

            </Form>

        </Container>
    );
}
export default CreateOrder;