import { Container, Image, Nav, Navbar, } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext } from "../App";
import { useContext, useState } from "react";
import { faBox, faCaretDown, faCog, faSignOutAlt, faTicket, faTicketAlt, faUserCog, faUserShield } from '@fortawesome/free-solid-svg-icons';
import '../static/css/Logo.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";


const Header = () => {
    const nav = useNavigate()
    const [isOpen, setIsOpen] = useState(null);
    const toggleDropdown = (dropdown) => {

        setIsOpen(isOpen === dropdown ? null : dropdown);
    };

    const [user, dispatch] = useContext(MyUserContext)



    const logout = () => {
        dispatch({
            'type': 'logout'
        })
        
    }
    return (
        <>

            <div className="header">
                <Navbar expand="lg">
                    <Container>

                        <Link to="/">
                            <Navbar.Brand >

                                <div className="logo">
                                    <div className="logo-icon">
                                        <Image width="50" height="50" src="https://img3.thuthuatphanmem.vn/uploads/2019/09/30/logo-hoa-nen-den_111809137.png" rounded />
                                    </div>
                                    <div className="logo-text">GIAO HÀNG GIÁ TỐT</div>
                                </div>
                            </Navbar.Brand>
                        </Link>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="me-auto">


                                <Link to="/">
                                    <div className="dropbtn" >TRANG CHỦ</div></Link>

                                <div className="dropdown"

                                    onMouseEnter={() => toggleDropdown('dropdown1')}
                                    onMouseLeave={() => setIsOpen(null)}
                                >

                                    <button className="dropbtn" onClick={toggleDropdown}>
                                        DỊCH VỤ <FontAwesomeIcon icon={faCaretDown} className={`dropdown-icon ${isOpen === 'dropdown1' ? 'open' : ''}`} />
                                    </button>
                                    {isOpen === 'dropdown1' && (
                                        <div className="dropdown-content">

                                            <Link to={user === null ? "/login" : "/createorder/hnorhcm"}>
                                                <FontAwesomeIcon icon={faBox} />
                                                <span className="dropdown-text">GIAO HÀNG NHANH (Trong nội địa TPHCM và Hà Nội)</span>
                                            </Link>
                                            <Link to={user === null ? "/login" : "/createorder"}>
                                                <FontAwesomeIcon icon={faBox} />
                                                <span className="dropdown-text">GIAO HÀNG TOÀN QUỐC</span>
                                            </Link>
                                            <Link to={user === null ? "/login" : "/orders/owner"}>
                                                <FontAwesomeIcon icon={faBox} />
                                                <span className="dropdown-text">XEM ĐƠN HÀNG ĐÃ CỦA BẠN</span>
                                            </Link>
                                            <Link to={user === null ? "/login" : "/vouchers"}>
                                                <FontAwesomeIcon icon={faTicket} />
                                                <span className="dropdown-text">XEM VOUCHER HIỆN CÓ</span>
                                            </Link>

                                        </div>
                                    )}
                                </div>

                                <div className="dropdown"

                                    onMouseEnter={() => {
                                        if (user !== null && user.data.roles.some(item => item.name === 'SHIPPER')
                                            && user.data.shipperActive) {
                                            toggleDropdown('dropdown2');

                                        }
                                    }}

                                    onMouseLeave={() => setIsOpen(null)}
                                >
                                    {user === null ? (
                                        <button className="dropbtn" onClick={toggleDropdown} disabled>
                                            NHẬN ĐƠN (DÀNH CHO SHIPPER)
                                            <FontAwesomeIcon icon={faCaretDown} className={`dropdown-icon ${isOpen === 'dropdown2' ? 'open' : ''}`} />
                                        </button>
                                    ) : (
                                        user.data.roles.some(item => item.name === 'SHIPPER') ? (
                                            user.data.shipperActive ? (
                                                <button className="dropbtn" onClick={toggleDropdown}>
                                                    NHẬN ĐƠN (DÀNH CHO SHIPPER)
                                                    <FontAwesomeIcon icon={faCaretDown} className={`dropdown-icon ${isOpen === 'dropdown2' ? 'open' : ''}`} />
                                                </button>
                                            ) : (
                                                <span className="text-danger dropbtn">Bạn chưa được xác nhận là shipper</span>
                                            )
                                        ) : (
                                            <button className="dropbtn" onClick={toggleDropdown} disabled>
                                                NHẬN ĐƠN (DÀNH CHO SHIPPER)
                                                <FontAwesomeIcon icon={faCaretDown} className={`dropdown-icon ${isOpen === 'dropdown2' ? 'open' : ''}`} />
                                            </button>
                                        )
                                    )}

                                    {isOpen === 'dropdown2' && (
                                        <div className="dropdown-content">

                                            <Link to={user === null ? "/login" : "/orders/NotYetPriced"}>
                                                <FontAwesomeIcon icon={faBox} />
                                                <span className="dropdown-text">XEM CÁC ĐƠN HÀNG</span>
                                            </Link>

                                            <Link to={user === null ? "/login" : "/orders/AwaitingSelection"}>
                                                <FontAwesomeIcon icon={faBox} />
                                                <span className="dropdown-text">ĐƠN CHỜ NHẬN</span>
                                            </Link>
                                            <Link to={user === null ? "/login" : "/orders/Selected"}>
                                                <FontAwesomeIcon icon={faBox} />
                                                <span className="dropdown-text">ĐƠN ĐÃ NHẬN</span>
                                            </Link>

                                        </div>
                                    )}
                                </div>

                                {user === null ?
                                    <Link to="/login">
                                        <button className="rounded-button">ĐĂNG KÝ/ ĐĂNG NHẬP</button>
                                    </Link> :
                                    <>

                                        <div className="dropdown"

                                            onMouseEnter={() => toggleDropdown('dropdown3')}
                                            onMouseLeave={() => setIsOpen(null)}
                                        >


                                            <button onClick={toggleDropdown} className="rounded-button `dropdown-icon ${isOpen === 'dropdown3' ? 'open' : ''}`">
                                                {user.data.username}</button>
                                            {isOpen === 'dropdown3' && (
                                                <div className="dropdown-content">

                                                    <Link to="/myprofile">
                                                        <FontAwesomeIcon icon={faCog} />
                                                        <span className="dropdown-text">MY PROFILE</span>
                                                    </Link>

                                                    {user.data.roles.some(item => item.name === 'ADMIN') ?
                                                        <Link to="/admin/customers">
                                                            <FontAwesomeIcon icon={faUserShield} />
                                                            <span className="dropdown-text">PAGE ADMIN</span>
                                                        </Link> : <></>
                                                    }


                                                    <Link to="/" onClick={logout}>
                                                        <FontAwesomeIcon icon={faSignOutAlt} />
                                                        <span className="dropdown-text">Đăng xuất</span>
                                                    </Link>

                                                </div>
                                            )}
                                        </div>

                                    </>
                                }


                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div >
        </>
    )
}
export default Header;