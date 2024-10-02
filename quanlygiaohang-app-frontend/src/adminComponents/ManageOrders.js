import React, { useEffect, useState } from 'react';
import '../static/css/AdminDashboard.css';
import { Button, Image, Spinner, Table } from 'react-bootstrap';
import { authApi, endpoint } from '../configs/Apis';
import Pagination from '../layout/Pagination';
import Filter from '../common/Filter';
import ButtonCheckPay from '../common/ButtonCheckPay';
import { useNavigate } from 'react-router-dom';
// import '../static/css/Table.css';

const ManagerOrder = () => {
    const [checkPay, setCheckPay] = useState(false)
    const [listOrder, setlistOrder] = useState(null)
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [totalPages, setTotalPages] = useState(2); // Tổng số trang
    const [filterData, setFilterData] = useState(null);
    const nav = useNavigate();
    const loadOrder = async (page) => {
        try {

            let form = new FormData()
            form.append("isPay", checkPay)
            if (filterData !== null) {
                for (let key in filterData) {
                    form.append(key, filterData[key])
                }
            }
            let url = `${endpoint['get-admin-orders']}?page=${page}`

            const res = await authApi().post(url, form)
            console.info(res)
            if (res.data.page.totalElements === 0) {
                setlistOrder([])
            }
            else setlistOrder(res.data._embedded.productList)
            setTotalPages(res.data.page.totalPages)


        }
        catch (ex) { console.log(ex) }
        finally { }
    }

    useEffect(() => {
        loadOrder(currentPage);

    }, [currentPage, filterData, checkPay]);
    const handlePageChange = (page) => {
        if (page < 1 || page > totalPages) return;
        setCurrentPage(page);

    };
    if (listOrder === null)
        return <Spinner animation="border" />;

    const handleFilter = (data) => {
        setCurrentPage(1)
        setFilterData(data);
    };
    return (
        <> <div style={{ overflow: "auto", height: "100%" }}>
            <Filter onSearch={handleFilter} /> : <></>
            <ButtonCheckPay onCheck={(value) => {
                setCheckPay(value)
            }} />
            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Tên mặt hàng</th>
                        <th>Số lượng</th>
                        <th>Dịch vụ</th>
                        <th>Tỉnh thành giao</th>
                        <th>Tỉnh thành nhận</th>
                        <th>Ngày tạo</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody style={{ height: '300px', scrollY: "auto" }} >
                    {listOrder.map(u =>
                        <tr>
                            <td>{u.id}</td>
                            <td>{u.name}</td>
                            <td>{u.quantity}</td>
                            <td>{u.serviceId.name}</td>
                            <td>{u.fromDistrictId.provinceId.provinceName}</td>
                            <td>{u.toDistrictId.provinceId.provinceName}</td>
                            <td>{u.createdDate}</td>
                            <td>
                                <Image height={'50px'} src={u.image} />
                            </td>
                            <td>
                                <Button variant='info' className='mr-2' onClick={() => nav(`/product/${u.id}?adminview=true`)}>Xem</Button>
                                {checkPay?<></>: <Button variant='danger'
                                onClick={()=>{
                                    authApi().delete(endpoint['delete-order'](u.id))
                                }}
                                >Xóa</Button>}
                               
                            </td>
                        </tr>)}


                </tbody>
            </Table>
        </div>
        </>
    );
};

export default ManagerOrder;
