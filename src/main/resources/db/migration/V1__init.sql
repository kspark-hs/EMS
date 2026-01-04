-- =========================
-- EMS / PMS V1 INIT SCHEMA
-- =========================

-- =========================
-- 사용자 (회원)
-- =========================
CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(50),
    authority VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- 발전소
-- =========================
CREATE TABLE plants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plant_name VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- 발전소-회원 매핑 (담당자)
-- =========================
CREATE TABLE plant_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plant_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    member_role VARCHAR(30) NOT NULL, -- OWNER / OPERATOR / SAFETY / OM / SYSTEM
    is_active BOOLEAN DEFAULT TRUE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pm_plant
        FOREIGN KEY (plant_id) REFERENCES plants(id),
    CONSTRAINT fk_pm_member
        FOREIGN KEY (member_id) REFERENCES members(id)
);

-- =========================
-- 설비 (공통)
-- =========================
CREATE TABLE devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plant_id BIGINT NOT NULL,
    device_type VARCHAR(50) NOT NULL,
    device_code VARCHAR(100) NOT NULL,
    name VARCHAR(255),
    status VARCHAR(30) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_device UNIQUE (plant_id, device_code),
    CONSTRAINT fk_device_plant
        FOREIGN KEY (plant_id) REFERENCES plants(id)
);
