create schema if not exists friend_management default character set utf8;
use friend_management;
drop table if exists user;
drop table if exists user_relation;

--
-- Database: `friend_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`) VALUES
('22b0b84f-cb9f-4bea-9fa5-c170df0802a0', 'andy@example.com'),
('0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', 'bagus@example.com'),
('fb39c41a-2c27-441f-9f32-4ed0f49c00d5', 'bob@example.com'),
('1e437a84-7cf0-47ab-9e13-29ecbf96b8e9', 'jacob@example.com'),
('2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'jane@example.com'),
('c66c1402-44be-4771-8c50-14d57d0c139b', 'john@example.com'),
('9108c22b-abb3-4e1d-9956-71e2c210b1eb', 'peralta@example.com'),
('407ffc69-766b-427c-865a-c70cefe685ec', 'simon@example.com');

-- --------------------------------------------------------

--
-- Table structure for table `user_relation`
--

CREATE TABLE `user_relation` (
  `id` varchar(36) NOT NULL,
  `related_id` varchar(36) NOT NULL,
  `relation_type` varchar(15) NOT NULL,
  `is_block` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_relation`
--

INSERT INTO `user_relation` (`id`, `related_id`, `relation_type`, `is_block`) VALUES
('0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', '1e437a84-7cf0-47ab-9e13-29ecbf96b8e9', 'FRIEND', 0),
('0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', '2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'FRIEND', 0),
('0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', '9108c22b-abb3-4e1d-9956-71e2c210b1eb', 'FRIEND', 0),
('1e437a84-7cf0-47ab-9e13-29ecbf96b8e9', '0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', 'FRIEND', 0),
('22b0b84f-cb9f-4bea-9fa5-c170df0802a0', '2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'FRIEND', 0),
('2c99bac2-6acf-4a0a-9730-601b21c4d16b', '0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', 'FRIEND', 0),
('2c99bac2-6acf-4a0a-9730-601b21c4d16b', '22b0b84f-cb9f-4bea-9fa5-c170df0802a0', 'FRIEND', 0),
('2c99bac2-6acf-4a0a-9730-601b21c4d16b', '407ffc69-766b-427c-865a-c70cefe685ec', 'FRIEND', 0),
('2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'c66c1402-44be-4771-8c50-14d57d0c139b', 'FRIEND', 0),
('407ffc69-766b-427c-865a-c70cefe685ec', '2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'FRIEND', 1),
('9108c22b-abb3-4e1d-9956-71e2c210b1eb', '0c8aab99-d371-475c-9bfc-fa68bfc9a3b4', 'FRIEND', 0),
('c66c1402-44be-4771-8c50-14d57d0c139b', '2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'FRIEND', 0),
('fb39c41a-2c27-441f-9f32-4ed0f49c00d5', '2c99bac2-6acf-4a0a-9730-601b21c4d16b', 'SUBSCRIBE', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_idx` (`email`);

--
-- Indexes for table `user_relation`
--
ALTER TABLE `user_relation`
  ADD PRIMARY KEY (`id`,`related_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
