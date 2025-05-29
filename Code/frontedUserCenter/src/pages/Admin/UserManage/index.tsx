import { searchUser } from '@/services/ant-design-pro/api';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { ProTable } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { useRef } from 'react';
export const waitTimePromise = async (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

export const waitTime = async (time: number = 100) => {
  await waitTimePromise(time);
};

const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '用户名',
    dataIndex: 'username',
    copyable: true,
  },
  {
    title: '用户角色',
    dataIndex: 'userRole',
    valueEnum: {
      1: { text: '管理员', status: 'success' },
      0: { text: '普通用户', status: 'processing' },
    },
  },
  {
    title: '用户账号',
    dataIndex: 'userAccount',
    copyable: true,
  },
  {
    title: '性别',
    dataIndex: 'gender',
    valueEnum: {
      1: { text: '男'},
      0: { text: '女'},
    },
  },
  {
    title: '手机号',
    dataIndex: 'phone',
    copyable: true,
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    copyable: true,
  },
  {
    title: '状态',
    dataIndex: 'status',
    valueEnum: {
      1: { text: '正常', status: 'success' },
      0: { text: '冻结', status: 'processing' },
    },
  },
  {
    title: '头像',
    dataIndex: 'avatarUrl',
    render: (_, entity) => (
      <img
        src={entity.avatarUrl || '/default-avatar.png'}
        alt="avatar"
        style={{
          width: 32,
          height: 32,
          borderRadius: '50%',
        }}
      />
    ),
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateTime',
    sorter: true,
  },
];

export default () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params, sort, filter) => {
        console.log(sort, filter);
        const userList: API.SearchUserResponse= await searchUser();
        return {
          data: userList.data
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        defaultValue: {
          option: { fixed: 'right', disable: true },
        },
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          listsHeight: 400,
        },
      }}
      form={{
        // 由于配置了 transform，提交的参数与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="高级表格"
    
    />
  );
};
