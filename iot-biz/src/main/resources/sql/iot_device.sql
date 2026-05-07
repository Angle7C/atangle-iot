create table if not exists angle.iot_device (
    id bigint not null,
    created_time timestamptz not null,
    updated_time timestamptz null,
    created_by_id bigint not null,
    created_by varchar(255) not null,
    updated_by_id bigint null,
    updated_by varchar(255) null,
    name varchar(100) not null,
    code varchar(100) not null,
    sn varchar(100) not null,
    product_id bigint not null,
    product_code varchar(100) null,
    device_type varchar(32) not null,
    status varchar(32) not null,
    online_state varchar(32) not null,
    access_token varchar(255) null,
    secret_key varchar(255) null,
    gateway_id bigint null,
    config jsonb null,
    remark varchar(500) null,
    active_time timestamptz null,
    last_online_time timestamptz null,
    last_offline_time timestamptz null,
    constraint iot_device_pk primary key (id),
    constraint iot_device_product_fk foreign key (product_id) references angle.iot_product(id)
);

create unique index if not exists iot_device_code_idx on angle.iot_device(code);
create unique index if not exists iot_device_sn_idx on angle.iot_device(sn);
create index if not exists iot_device_product_id_idx on angle.iot_device(product_id);
create index if not exists iot_device_gateway_id_idx on angle.iot_device(gateway_id);
create index if not exists iot_device_status_idx on angle.iot_device(status);

create table if not exists angle.iot_device_attr (
    id bigint not null,
    created_time timestamptz not null,
    updated_time timestamptz null,
    created_by_id bigint not null,
    created_by varchar(255) not null,
    updated_by_id bigint null,
    updated_by varchar(255) null,
    device_id bigint not null,
    product_attr_id bigint null,
    attr_code varchar(100) not null,
    attr_name varchar(100) not null,
    value_type varchar(64) null,
    value jsonb null,
    source varchar(32) null,
    quality integer null,
    remark varchar(500) null,
    report_time timestamptz null,
    write_time timestamptz null,
    constraint iot_device_attr_pk primary key (id),
    constraint iot_device_attr_device_fk foreign key (device_id) references angle.iot_device(id),
    constraint iot_device_attr_product_attr_fk foreign key (product_attr_id) references angle.iot_product_attr(id)
);

create unique index if not exists iot_device_attr_device_attr_code_idx on angle.iot_device_attr(device_id, attr_code);
create unique index if not exists iot_device_attr_device_product_attr_idx on angle.iot_device_attr(device_id, product_attr_id) where product_attr_id is not null;
create index if not exists iot_device_attr_report_time_idx on angle.iot_device_attr(report_time);

create table if not exists angle.iot_device_service_log (
    id bigint not null,
    created_time timestamptz not null,
    updated_time timestamptz null,
    created_by_id bigint not null,
    created_by varchar(255) not null,
    updated_by_id bigint null,
    updated_by varchar(255) null,
    device_id bigint not null,
    product_service_id bigint null,
    service_code varchar(100) not null,
    input_param jsonb null,
    output_result jsonb null,
    status varchar(32) not null,
    message varchar(500) null,
    trace_id varchar(128) null,
    invoke_time timestamptz null,
    finish_time timestamptz null,
    constraint iot_device_service_log_pk primary key (id),
    constraint iot_device_service_log_device_fk foreign key (device_id) references angle.iot_device(id),
    constraint iot_device_service_log_product_service_fk foreign key (product_service_id) references angle.iot_product_service(id)
);

create index if not exists iot_device_service_log_device_id_idx on angle.iot_device_service_log(device_id);
create index if not exists iot_device_service_log_status_idx on angle.iot_device_service_log(status);
create index if not exists iot_device_service_log_invoke_time_idx on angle.iot_device_service_log(invoke_time);
create index if not exists iot_device_service_log_trace_id_idx on angle.iot_device_service_log(trace_id);

create table if not exists angle.iot_device_event_log (
    id bigint not null,
    created_time timestamptz not null,
    updated_time timestamptz null,
    created_by_id bigint not null,
    created_by varchar(255) not null,
    updated_by_id bigint null,
    updated_by varchar(255) null,
    device_id bigint not null,
    product_event_id bigint null,
    event_code varchar(100) not null,
    event_type varchar(64) null,
    output_data jsonb null,
    source varchar(32) null,
    trace_id varchar(128) null,
    event_time timestamptz null,
    constraint iot_device_event_log_pk primary key (id),
    constraint iot_device_event_log_device_fk foreign key (device_id) references angle.iot_device(id),
    constraint iot_device_event_log_product_event_fk foreign key (product_event_id) references angle.iot_product_event(id)
);

create index if not exists iot_device_event_log_device_id_idx on angle.iot_device_event_log(device_id);
create index if not exists iot_device_event_log_event_code_idx on angle.iot_device_event_log(event_code);
create index if not exists iot_device_event_log_event_time_idx on angle.iot_device_event_log(event_time);
create index if not exists iot_device_event_log_trace_id_idx on angle.iot_device_event_log(trace_id);
