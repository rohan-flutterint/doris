import java.util.stream.Collectors

// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

suite("test_analyze") {

    def stats_dropped = { table ->
        def result1 = sql """show column cached stats $table"""
        def result2 = sql """show column stats $table"""
        boolean dropped = false
        for (int i = 0; i < 120; i++) {
            if (0 == result1.size() && 0 == result2.size()) {
                dropped = true;
                break;
            }
            Thread.sleep(1000)
            result1 = sql """show column cached stats $table"""
            result2 = sql """show column stats $table"""
        }
        assertTrue(dropped)
    }

    String db = "test_analyze"

    String tbl = "analyzetestlimited_duplicate_all"

    sql """set global force_sample_analyze=false"""
    sql """set global enable_auto_analyze=false"""
    sql """
        DROP DATABASE IF EXISTS `${db}`
    """

    sql """
        CREATE DATABASE `${db}`
    """

    // regression framework will auto create an default DB with name regression_test_$(case dir name) and use it to run case,
    // if we do not use the default DB, here we should use the custom DB explicitly
    sql """
        USE `${db}`
    """

    sql """
        DROP TABLE IF EXISTS `${tbl}`
    """


    sql """
          CREATE TABLE IF NOT EXISTS `${tbl}` (
            `analyzetestlimitedk3` int(11) null comment "",
            `analyzetestlimitedk0` boolean null comment "",
            `analyzetestlimitedk1` tinyint(4) null comment "",
            `analyzetestlimitedk2` smallint(6) null comment "",
            `analyzetestlimitedk4` bigint(20) null comment "",
            `analyzetestlimitedk5` decimalv3(9, 3) null comment "",
            `analyzetestlimitedk6` char(36) null comment "",
            `analyzetestlimitedk10` date null comment "",
            `analyzetestlimitedk11` datetime null comment "",
            `analyzetestlimitedk7` varchar(64) null comment "",
            `analyzetestlimitedk8` double null comment "",
            `analyzetestlimitedk9` float null comment "",
            `analyzetestlimitedk12` string  null comment "",
            `analyzetestlimitedk13` largeint(40)  null comment "",
            `analyzetestlimitedk14` ARRAY<int(11)> NULL COMMENT "",
            `analyzetestlimitedk15` Map<STRING, INT> NULL COMMENT "",
            `analyzetestlimitedk16` STRUCT<s_id:int(11), s_name:string, s_address:string> NULL,
            `analyzetestlimitedk17` JSON NULL
        ) engine=olap
        DUPLICATE KEY(`analyzetestlimitedk3`)
        DISTRIBUTED BY HASH(`analyzetestlimitedk3`) BUCKETS 5 properties("replication_num" = "1")
    """

    sql """
        INSERT INTO `${tbl}` VALUES (-2103297891,1,101,15248,4761818404925265645,939926.283,
        'UTmCFKMbprf0zSVOIlBJRNOl3JcNBdOsnCDt','2022-09-28','2022-10-28 01:56:56','tVvGDSrN6kyn',
        -954349107.187117,-40.46286,'g1ZP9nqVgaGKya3kPERdBofTWJQ4TIJEz972Xvw4hfPpTpWwlmondiLVTCyld7rSBlSWrE7NJRB0pvPGEFQKOx1s3',
        '-1559301292834325905', NULL, NULL, NULL, NULL),
        (-2094982029,0,-81,-14746,-2618177187906633064,121889.100,NULL,'2023-05-01','2022-11-25 00:24:12',
        '36jVI0phYfhFucAOEASbh4OdvUYcI7QZFgQSveNyfGcRRUtQG9HGN1UcCmUH',-82250254.174239,NULL,
        'bTUHnMC4v7dI8U3TK0z4wZHdytjfHQfF1xKdYAVwPVNMT4fT4F92hj8ENQXmCkWtfp','6971810221218612372', NULL, NULL, NULL, NULL),
        (-1840301109,1,NULL,NULL,7805768460922079440,546556.220,'wC7Pif9SJrg9b0wicGfPz2ezEmEKotmN6AMI',NULL,
        '2023-05-20 18:13:14','NM5SLu62SGeuD',-1555800813.9748349,-11122.953,
        'NH97wIjXk7dspvvfUUKe41ZetUnDmqLxGg8UYXwOwK3Jlu7dxO2GE9UJjyKW0NBxqUk1DY','-5004534044262380098', NULL, NULL, NULL, NULL),
        (-1819679967,0,10,NULL,-5772413527188525359,-532045.626,'kqMe4VYEZAmajLthCLRkl8StDQHKrDWz91AQ','2022-06-30',
        '2023-02-22 15:30:38','wAbeF3p84j5pFJTInQuKZOezFbsy8HIjmuUF',-1766437367.4377379,1791.4128,
        '6OWmBD04UeKt1xI2XnR8t1kPG7qEYrf4J8RkA8UMs4HF33Yl','-8433424551792664598', NULL, NULL, NULL, NULL),
        (-1490846276,0,NULL,7744,6074522476276146996,594200.976,NULL,'2022-11-27','2023-03-11 21:28:44',
        'yr8AuJLr2ud7DIwlt06cC7711UOsKslcDyySuqqfQE5X7Vjic6azHOrM6W',-715849856.288922,3762.217,
        '4UpWZJ0Twrefw0Tm0AxFS38V5','7406302706201801560', NULL, NULL, NULL, NULL),(-1465848366,1,72,29170,-5585523608136628843,-34210.874,
        'rMGygAWU91Wa3b5A7l1wheo6EF0o6zhw4YeE','2022-09-20','2023-06-11 18:17:16','B6m9S9O2amsa4SXrEKK0ivJ2x9m1u8av',
        862085772.298349,-22304.209,'1','-3399178642401166400', NULL, NULL, NULL, NULL),(-394034614,1,65,5393,-200651968801088119,NULL,
        '9MapWX9pn8zes9Gey1lhRsH3ATyQPIysjQYi','2023-05-11','2022-07-02 02:56:53','z5VWbuKr6HiK7yC7MRIoQGrb98VUS',
        1877828963.091433,-1204.1926,'fSDQqT38rkrJEi6fwc90rivgQcRPaW5V1aEmZpdSvUm','8882970420609470903', NULL, NULL, NULL, NULL),
        (-287465855,0,-10,-32484,-5161845307234178602,748718.592,'n64TXbG25DQL5aw5oo9o9cowSjHCXry9HkId','2023-01-02',
        '2022-11-17 14:58:52','d523m4PwLdHZtPTqSoOBo5IGivCKe4A1Sc8SKCILFxgzYLe0',NULL,27979.855,
        'ps7qwcZjBjkGfcXYMw5HQMwnElzoHqinwk8vhQCbVoGBgfotc4oSkpD3tP34h4h0tTogDMwFu60iJm1bofUzyUQofTeRwZk8','4692206687866847780', NULL, NULL, NULL, NULL)
    """

    sql """
        SET enable_nereids_planner=true;

    """

    sql """
        SET forbid_unknown_col_stats=false;
    """

    sql """
        SELECT * FROM ${tbl}
    """

    sql """
        ANALYZE DATABASE ${db}
    """

    sql """
        ANALYZE DATABASE ${db} WITH SYNC
    """

    sql """
        SET enable_fallback_to_original_planner=false;
        """
    sql """
        SET forbid_unknown_col_stats=true;
        """

    sql """
        SELECT * FROM ${tbl};
    """

    sql """
        DROP STATS ${tbl}(analyzetestlimitedk3)
    """

    def exception = null

    try {
        sql """
            SELECT * FROM ${tbl};
        """
    } catch (Exception e) {
        exception = e
    }

    assert exception != null

    exception = null

    sql """
        ANALYZE TABLE ${tbl} WITH SYNC
    """

    sql """
        SELECT * FROM ${tbl};
    """

    sql """
        DROP STATS ${tbl}
    """

    try {
        sql """
            SELECT * FROM ${tbl};
        """
    } catch (Exception e) {
        exception = e
    }

    // Test partition load data for the first time.
    sql """
     CREATE TABLE `partition_test` (
      `id` INT NOT NULL,
      `name` VARCHAR(25) NOT NULL,
      `comment` VARCHAR(152) NULL
      ) ENGINE=OLAP
      DUPLICATE KEY(`id`)
      COMMENT 'OLAP'
      PARTITION BY RANGE(`id`)
      (PARTITION p1 VALUES [("0"), ("100")),
       PARTITION p2 VALUES [("100"), ("200")),
       PARTITION p3 VALUES [("200"), ("300")))
      DISTRIBUTED BY HASH(`id`) BUCKETS 1
      PROPERTIES (
       "replication_num" = "1");
     """

    sql """analyze table partition_test with sync"""
    sql """insert into partition_test values (1, '1', '1')"""
    def partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "true")
    assertEquals(partition_result[0][0], "1")
    sql """analyze table partition_test with sync"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "false")
    sql """insert into partition_test values (101, '1', '1')"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "true")
    sql """analyze table partition_test(id) with sync"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "true")
    sql """analyze table partition_test with sync"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "false")
    sql """insert into partition_test values (102, '1', '1')"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "false")
    sql """insert into partition_test values (2, '2', '2')"""
    sql """insert into partition_test values (3, '3', '3')"""
    sql """insert into partition_test values (4, '4', '4')"""
    sql """insert into partition_test values (5, '5', '5')"""
    sql """insert into partition_test values (6, '6', '6')"""
    sql """insert into partition_test values (7, '7', '7')"""
    sql """insert into partition_test values (8, '8', '8')"""
    sql """insert into partition_test values (9, '9', '9')"""
    sql """insert into partition_test values (10, '10', '10')"""
    sql """insert into partition_test values (103, '1', '1')"""
    sql """insert into partition_test values (104, '1', '1')"""
    sql """insert into partition_test values (105, '1', '1')"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "false")
    sql """analyze table partition_test with sync"""
    sql """insert into partition_test values (201, '1', '1')"""
    partition_result = sql """show table stats partition_test"""
    assertEquals(partition_result[0][6], "true")
    partition_result = sql """show column stats partition_test(id)"""
    assertEquals("id", partition_result[0][0])
    assertEquals("15.0", partition_result[0][2])
    partition_result = sql """show column stats partition_test(name)"""
    assertEquals("name", partition_result[0][0])
    assertEquals("15.0", partition_result[0][2])
    partition_result = sql """show column stats partition_test(comment)"""
    assertEquals("comment", partition_result[0][0])
    assertEquals("15.0", partition_result[0][2])


    // Test sample agg table value column. Min max is N/A when zone map is not available.
    sql """
     CREATE TABLE `agg_table_test` (
      `id` BIGINT NOT NULL,
      `name` VARCHAR(10) REPLACE NULL
     ) ENGINE=OLAP
     AGGREGATE KEY(`id`)
     COMMENT 'OLAP'
     DISTRIBUTED BY HASH(`id`) BUCKETS 32
     PROPERTIES (
      "replication_num" = "1"
     );
    """
    sql """insert into agg_table_test values (1,'name1'), (2, 'name2')"""
    sql """analyze table agg_table_test with sample rows 100 with sync"""
    def agg_result = sql """show column stats agg_table_test (name)"""
    logger.info("show column agg_table_test(name) stats: " + agg_result)
    assertEquals(0, agg_result.size())

    // Continue test partition load data for the first time.
    def reported = false;
    for (int i = 0; i < 10; i++) {
        def data_result = sql """show data from partition_test"""
        logger.info("show data from partition_test: " + data_result)
        if (data_result[0][4] == '16') {
            reported = true;
            break;
        }
        sleep(1000)
    }
    if (!reported) {
        logger.info("partition_test row count is not reported.")
    } else {
        sql """analyze table partition_test(name) PROPERTIES("use.auto.analyzer"="true")"""
        partition_result = sql """show table stats partition_test"""
        assertEquals(partition_result[0][6], "false")
        partition_result = sql """show column stats partition_test(id)"""
        assertEquals("id", partition_result[0][0])
        assertEquals("16.0", partition_result[0][2])
        partition_result = sql """show column stats partition_test(name)"""
        assertEquals("name", partition_result[0][0])
        assertEquals("16.0", partition_result[0][2])
        partition_result = sql """show column stats partition_test(comment)"""
        assertEquals("comment", partition_result[0][0])
        assertEquals("16.0", partition_result[0][2])
    }

    def a_result_1 = sql """
        ANALYZE DATABASE ${db} WITH SYNC WITH SAMPLE PERCENT 10
    """

    def a_result_3 = sql """
        ANALYZE DATABASE ${db} WITH SAMPLE PERCENT 5
    """

    def show_result = sql """
        SHOW ANALYZE
    """

    def contains_expected_table = { r ->
        for (int i = 0; i < r.size(); i++) {
            if (r[i][3] == "${tbl}") {
                return true
            }
        }
        return false
    }

    def stats_job_removed = { r, id ->
        for (int i = 0; i < r.size(); i++) {
            if (r[i][0] == id) {
                return false
            }
        }
        return true
    }

    assert contains_expected_table(show_result)

    sql """
        DROP ANALYZE JOB ${a_result_3[0][0]}
    """

    show_result = sql """
        SHOW ANALYZE
    """

    assert stats_job_removed(show_result, a_result_3[0][0])

    sql """
        ANALYZE DATABASE ${db} WITH SAMPLE ROWS 5 WITH PERIOD 100000
    """

    sql """
        DROP TABLE IF EXISTS analyze_partitioned_tbl_test
    """

    sql """
        CREATE TABLE analyze_partitioned_tbl_test (col1 int, col2 int, col3 int)
        PARTITION BY RANGE(`col2`) (
            PARTITION `p1` VALUES LESS THAN ('5'),
            PARTITION `p2` VALUES LESS THAN ('10'),
            PARTITION `P3` VALUES LESS THAN ('15'),
            PARTITION `P4` VALUES LESS THAN ('20'),
            PARTITION `P5` VALUES LESS THAN ('25'),
            PARTITION `P6` VALUES LESS THAN ('30'))
        DISTRIBUTED BY HASH(col3)
        BUCKETS 3
        PROPERTIES(
            "replication_num"="1"
        )
    """

    sql """insert into analyze_partitioned_tbl_test values(1,3,1) """
    sql """insert into analyze_partitioned_tbl_test values(6,6,6) """
    sql """insert into analyze_partitioned_tbl_test values(11,6,6) """
    sql """insert into analyze_partitioned_tbl_test values(16,6,6) """
    sql """insert into analyze_partitioned_tbl_test values(21,6,6) """
    sql """insert into analyze_partitioned_tbl_test values(26,6,6) """

    sql """
        ANALYZE TABLE analyze_partitioned_tbl_test WITH SYNC
    """

    def part_tbl_analyze_result = sql """
        SHOW COLUMN CACHED STATS analyze_partitioned_tbl_test(col1)
    """

    def expected_result = { r->
        for(int i = 0; i < r.size(); i++) {
            if ((int) Double.parseDouble(r[i][2]) == 6) {
                return true
            } else {
                return false
            }
        }
        return false
    }

    assert expected_result(part_tbl_analyze_result)

    sql """
        DROP TABLE IF EXISTS test_600_partition_table_analyze;
    """

    sql """

    CREATE TAbLE IF NOT EXISTS test_600_partition_table_analyze (
    `id` INT NOT NULL
    ) ENGINE=OLAP
    DUPLICATE KEY(id)
    PARTITION BY LIST(id)
    (
    PARTITION `p0` VALUES IN (0),
PARTITION `p1` VALUES IN (1),
PARTITION `p2` VALUES IN (2),
PARTITION `p3` VALUES IN (3),
PARTITION `p4` VALUES IN (4),
PARTITION `p5` VALUES IN (5),
PARTITION `p6` VALUES IN (6),
PARTITION `p7` VALUES IN (7),
PARTITION `p8` VALUES IN (8),
PARTITION `p9` VALUES IN (9),
PARTITION `p10` VALUES IN (10),
PARTITION `p11` VALUES IN (11),
PARTITION `p12` VALUES IN (12),
PARTITION `p13` VALUES IN (13),
PARTITION `p14` VALUES IN (14),
PARTITION `p15` VALUES IN (15),
PARTITION `p16` VALUES IN (16),
PARTITION `p17` VALUES IN (17),
PARTITION `p18` VALUES IN (18),
PARTITION `p19` VALUES IN (19),
PARTITION `p20` VALUES IN (20),
PARTITION `p21` VALUES IN (21),
PARTITION `p22` VALUES IN (22),
PARTITION `p23` VALUES IN (23),
PARTITION `p24` VALUES IN (24),
PARTITION `p25` VALUES IN (25),
PARTITION `p26` VALUES IN (26),
PARTITION `p27` VALUES IN (27),
PARTITION `p28` VALUES IN (28),
PARTITION `p29` VALUES IN (29),
PARTITION `p30` VALUES IN (30),
PARTITION `p31` VALUES IN (31),
PARTITION `p32` VALUES IN (32),
PARTITION `p33` VALUES IN (33),
PARTITION `p34` VALUES IN (34),
PARTITION `p35` VALUES IN (35),
PARTITION `p36` VALUES IN (36),
PARTITION `p37` VALUES IN (37),
PARTITION `p38` VALUES IN (38),
PARTITION `p39` VALUES IN (39),
PARTITION `p40` VALUES IN (40),
PARTITION `p41` VALUES IN (41),
PARTITION `p42` VALUES IN (42),
PARTITION `p43` VALUES IN (43),
PARTITION `p44` VALUES IN (44),
PARTITION `p45` VALUES IN (45),
PARTITION `p46` VALUES IN (46),
PARTITION `p47` VALUES IN (47),
PARTITION `p48` VALUES IN (48),
PARTITION `p49` VALUES IN (49),
PARTITION `p50` VALUES IN (50),
PARTITION `p51` VALUES IN (51),
PARTITION `p52` VALUES IN (52),
PARTITION `p53` VALUES IN (53),
PARTITION `p54` VALUES IN (54),
PARTITION `p55` VALUES IN (55),
PARTITION `p56` VALUES IN (56),
PARTITION `p57` VALUES IN (57),
PARTITION `p58` VALUES IN (58),
PARTITION `p59` VALUES IN (59),
PARTITION `p60` VALUES IN (60),
PARTITION `p61` VALUES IN (61),
PARTITION `p62` VALUES IN (62),
PARTITION `p63` VALUES IN (63),
PARTITION `p64` VALUES IN (64),
PARTITION `p65` VALUES IN (65),
PARTITION `p66` VALUES IN (66),
PARTITION `p67` VALUES IN (67),
PARTITION `p68` VALUES IN (68),
PARTITION `p69` VALUES IN (69),
PARTITION `p70` VALUES IN (70),
PARTITION `p71` VALUES IN (71),
PARTITION `p72` VALUES IN (72),
PARTITION `p73` VALUES IN (73),
PARTITION `p74` VALUES IN (74),
PARTITION `p75` VALUES IN (75),
PARTITION `p76` VALUES IN (76),
PARTITION `p77` VALUES IN (77),
PARTITION `p78` VALUES IN (78),
PARTITION `p79` VALUES IN (79),
PARTITION `p80` VALUES IN (80),
PARTITION `p81` VALUES IN (81),
PARTITION `p82` VALUES IN (82),
PARTITION `p83` VALUES IN (83),
PARTITION `p84` VALUES IN (84),
PARTITION `p85` VALUES IN (85),
PARTITION `p86` VALUES IN (86),
PARTITION `p87` VALUES IN (87),
PARTITION `p88` VALUES IN (88),
PARTITION `p89` VALUES IN (89),
PARTITION `p90` VALUES IN (90),
PARTITION `p91` VALUES IN (91),
PARTITION `p92` VALUES IN (92),
PARTITION `p93` VALUES IN (93),
PARTITION `p94` VALUES IN (94),
PARTITION `p95` VALUES IN (95),
PARTITION `p96` VALUES IN (96),
PARTITION `p97` VALUES IN (97),
PARTITION `p98` VALUES IN (98),
PARTITION `p99` VALUES IN (99),
PARTITION `p100` VALUES IN (100),
PARTITION `p101` VALUES IN (101),
PARTITION `p102` VALUES IN (102),
PARTITION `p103` VALUES IN (103),
PARTITION `p104` VALUES IN (104),
PARTITION `p105` VALUES IN (105),
PARTITION `p106` VALUES IN (106),
PARTITION `p107` VALUES IN (107),
PARTITION `p108` VALUES IN (108),
PARTITION `p109` VALUES IN (109),
PARTITION `p110` VALUES IN (110),
PARTITION `p111` VALUES IN (111),
PARTITION `p112` VALUES IN (112),
PARTITION `p113` VALUES IN (113),
PARTITION `p114` VALUES IN (114),
PARTITION `p115` VALUES IN (115),
PARTITION `p116` VALUES IN (116),
PARTITION `p117` VALUES IN (117),
PARTITION `p118` VALUES IN (118),
PARTITION `p119` VALUES IN (119),
PARTITION `p120` VALUES IN (120),
PARTITION `p121` VALUES IN (121),
PARTITION `p122` VALUES IN (122),
PARTITION `p123` VALUES IN (123),
PARTITION `p124` VALUES IN (124),
PARTITION `p125` VALUES IN (125),
PARTITION `p126` VALUES IN (126),
PARTITION `p127` VALUES IN (127),
PARTITION `p128` VALUES IN (128),
PARTITION `p129` VALUES IN (129),
PARTITION `p130` VALUES IN (130),
PARTITION `p131` VALUES IN (131),
PARTITION `p132` VALUES IN (132),
PARTITION `p133` VALUES IN (133),
PARTITION `p134` VALUES IN (134),
PARTITION `p135` VALUES IN (135),
PARTITION `p136` VALUES IN (136),
PARTITION `p137` VALUES IN (137),
PARTITION `p138` VALUES IN (138),
PARTITION `p139` VALUES IN (139),
PARTITION `p140` VALUES IN (140),
PARTITION `p141` VALUES IN (141),
PARTITION `p142` VALUES IN (142),
PARTITION `p143` VALUES IN (143),
PARTITION `p144` VALUES IN (144),
PARTITION `p145` VALUES IN (145),
PARTITION `p146` VALUES IN (146),
PARTITION `p147` VALUES IN (147),
PARTITION `p148` VALUES IN (148),
PARTITION `p149` VALUES IN (149),
PARTITION `p150` VALUES IN (150),
PARTITION `p151` VALUES IN (151),
PARTITION `p152` VALUES IN (152),
PARTITION `p153` VALUES IN (153),
PARTITION `p154` VALUES IN (154),
PARTITION `p155` VALUES IN (155),
PARTITION `p156` VALUES IN (156),
PARTITION `p157` VALUES IN (157),
PARTITION `p158` VALUES IN (158),
PARTITION `p159` VALUES IN (159),
PARTITION `p160` VALUES IN (160),
PARTITION `p161` VALUES IN (161),
PARTITION `p162` VALUES IN (162),
PARTITION `p163` VALUES IN (163),
PARTITION `p164` VALUES IN (164),
PARTITION `p165` VALUES IN (165),
PARTITION `p166` VALUES IN (166),
PARTITION `p167` VALUES IN (167),
PARTITION `p168` VALUES IN (168),
PARTITION `p169` VALUES IN (169),
PARTITION `p170` VALUES IN (170),
PARTITION `p171` VALUES IN (171),
PARTITION `p172` VALUES IN (172),
PARTITION `p173` VALUES IN (173),
PARTITION `p174` VALUES IN (174),
PARTITION `p175` VALUES IN (175),
PARTITION `p176` VALUES IN (176),
PARTITION `p177` VALUES IN (177),
PARTITION `p178` VALUES IN (178),
PARTITION `p179` VALUES IN (179),
PARTITION `p180` VALUES IN (180),
PARTITION `p181` VALUES IN (181),
PARTITION `p182` VALUES IN (182),
PARTITION `p183` VALUES IN (183),
PARTITION `p184` VALUES IN (184),
PARTITION `p185` VALUES IN (185),
PARTITION `p186` VALUES IN (186),
PARTITION `p187` VALUES IN (187),
PARTITION `p188` VALUES IN (188),
PARTITION `p189` VALUES IN (189),
PARTITION `p190` VALUES IN (190),
PARTITION `p191` VALUES IN (191),
PARTITION `p192` VALUES IN (192),
PARTITION `p193` VALUES IN (193),
PARTITION `p194` VALUES IN (194),
PARTITION `p195` VALUES IN (195),
PARTITION `p196` VALUES IN (196),
PARTITION `p197` VALUES IN (197),
PARTITION `p198` VALUES IN (198),
PARTITION `p199` VALUES IN (199),
PARTITION `p200` VALUES IN (200),
PARTITION `p201` VALUES IN (201),
PARTITION `p202` VALUES IN (202),
PARTITION `p203` VALUES IN (203),
PARTITION `p204` VALUES IN (204),
PARTITION `p205` VALUES IN (205),
PARTITION `p206` VALUES IN (206),
PARTITION `p207` VALUES IN (207),
PARTITION `p208` VALUES IN (208),
PARTITION `p209` VALUES IN (209),
PARTITION `p210` VALUES IN (210),
PARTITION `p211` VALUES IN (211),
PARTITION `p212` VALUES IN (212),
PARTITION `p213` VALUES IN (213),
PARTITION `p214` VALUES IN (214),
PARTITION `p215` VALUES IN (215),
PARTITION `p216` VALUES IN (216),
PARTITION `p217` VALUES IN (217),
PARTITION `p218` VALUES IN (218),
PARTITION `p219` VALUES IN (219),
PARTITION `p220` VALUES IN (220),
PARTITION `p221` VALUES IN (221),
PARTITION `p222` VALUES IN (222),
PARTITION `p223` VALUES IN (223),
PARTITION `p224` VALUES IN (224),
PARTITION `p225` VALUES IN (225),
PARTITION `p226` VALUES IN (226),
PARTITION `p227` VALUES IN (227),
PARTITION `p228` VALUES IN (228),
PARTITION `p229` VALUES IN (229),
PARTITION `p230` VALUES IN (230),
PARTITION `p231` VALUES IN (231),
PARTITION `p232` VALUES IN (232),
PARTITION `p233` VALUES IN (233),
PARTITION `p234` VALUES IN (234),
PARTITION `p235` VALUES IN (235),
PARTITION `p236` VALUES IN (236),
PARTITION `p237` VALUES IN (237),
PARTITION `p238` VALUES IN (238),
PARTITION `p239` VALUES IN (239),
PARTITION `p240` VALUES IN (240),
PARTITION `p241` VALUES IN (241),
PARTITION `p242` VALUES IN (242),
PARTITION `p243` VALUES IN (243),
PARTITION `p244` VALUES IN (244),
PARTITION `p245` VALUES IN (245),
PARTITION `p246` VALUES IN (246),
PARTITION `p247` VALUES IN (247),
PARTITION `p248` VALUES IN (248),
PARTITION `p249` VALUES IN (249),
PARTITION `p250` VALUES IN (250),
PARTITION `p251` VALUES IN (251),
PARTITION `p252` VALUES IN (252),
PARTITION `p253` VALUES IN (253),
PARTITION `p254` VALUES IN (254),
PARTITION `p255` VALUES IN (255),
PARTITION `p256` VALUES IN (256),
PARTITION `p257` VALUES IN (257),
PARTITION `p258` VALUES IN (258),
PARTITION `p259` VALUES IN (259),
PARTITION `p260` VALUES IN (260),
PARTITION `p261` VALUES IN (261),
PARTITION `p262` VALUES IN (262),
PARTITION `p263` VALUES IN (263),
PARTITION `p264` VALUES IN (264),
PARTITION `p265` VALUES IN (265),
PARTITION `p266` VALUES IN (266),
PARTITION `p267` VALUES IN (267),
PARTITION `p268` VALUES IN (268),
PARTITION `p269` VALUES IN (269),
PARTITION `p270` VALUES IN (270),
PARTITION `p271` VALUES IN (271),
PARTITION `p272` VALUES IN (272),
PARTITION `p273` VALUES IN (273),
PARTITION `p274` VALUES IN (274),
PARTITION `p275` VALUES IN (275),
PARTITION `p276` VALUES IN (276),
PARTITION `p277` VALUES IN (277),
PARTITION `p278` VALUES IN (278),
PARTITION `p279` VALUES IN (279),
PARTITION `p280` VALUES IN (280),
PARTITION `p281` VALUES IN (281),
PARTITION `p282` VALUES IN (282),
PARTITION `p283` VALUES IN (283),
PARTITION `p284` VALUES IN (284),
PARTITION `p285` VALUES IN (285),
PARTITION `p286` VALUES IN (286),
PARTITION `p287` VALUES IN (287),
PARTITION `p288` VALUES IN (288),
PARTITION `p289` VALUES IN (289),
PARTITION `p290` VALUES IN (290),
PARTITION `p291` VALUES IN (291),
PARTITION `p292` VALUES IN (292),
PARTITION `p293` VALUES IN (293),
PARTITION `p294` VALUES IN (294),
PARTITION `p295` VALUES IN (295),
PARTITION `p296` VALUES IN (296),
PARTITION `p297` VALUES IN (297),
PARTITION `p298` VALUES IN (298),
PARTITION `p299` VALUES IN (299),
PARTITION `p300` VALUES IN (300),
PARTITION `p301` VALUES IN (301),
PARTITION `p302` VALUES IN (302),
PARTITION `p303` VALUES IN (303),
PARTITION `p304` VALUES IN (304),
PARTITION `p305` VALUES IN (305),
PARTITION `p306` VALUES IN (306),
PARTITION `p307` VALUES IN (307),
PARTITION `p308` VALUES IN (308),
PARTITION `p309` VALUES IN (309),
PARTITION `p310` VALUES IN (310),
PARTITION `p311` VALUES IN (311),
PARTITION `p312` VALUES IN (312),
PARTITION `p313` VALUES IN (313),
PARTITION `p314` VALUES IN (314),
PARTITION `p315` VALUES IN (315),
PARTITION `p316` VALUES IN (316),
PARTITION `p317` VALUES IN (317),
PARTITION `p318` VALUES IN (318),
PARTITION `p319` VALUES IN (319),
PARTITION `p320` VALUES IN (320),
PARTITION `p321` VALUES IN (321),
PARTITION `p322` VALUES IN (322),
PARTITION `p323` VALUES IN (323),
PARTITION `p324` VALUES IN (324),
PARTITION `p325` VALUES IN (325),
PARTITION `p326` VALUES IN (326),
PARTITION `p327` VALUES IN (327),
PARTITION `p328` VALUES IN (328),
PARTITION `p329` VALUES IN (329),
PARTITION `p330` VALUES IN (330),
PARTITION `p331` VALUES IN (331),
PARTITION `p332` VALUES IN (332),
PARTITION `p333` VALUES IN (333),
PARTITION `p334` VALUES IN (334),
PARTITION `p335` VALUES IN (335),
PARTITION `p336` VALUES IN (336),
PARTITION `p337` VALUES IN (337),
PARTITION `p338` VALUES IN (338),
PARTITION `p339` VALUES IN (339),
PARTITION `p340` VALUES IN (340),
PARTITION `p341` VALUES IN (341),
PARTITION `p342` VALUES IN (342),
PARTITION `p343` VALUES IN (343),
PARTITION `p344` VALUES IN (344),
PARTITION `p345` VALUES IN (345),
PARTITION `p346` VALUES IN (346),
PARTITION `p347` VALUES IN (347),
PARTITION `p348` VALUES IN (348),
PARTITION `p349` VALUES IN (349),
PARTITION `p350` VALUES IN (350),
PARTITION `p351` VALUES IN (351),
PARTITION `p352` VALUES IN (352),
PARTITION `p353` VALUES IN (353),
PARTITION `p354` VALUES IN (354),
PARTITION `p355` VALUES IN (355),
PARTITION `p356` VALUES IN (356),
PARTITION `p357` VALUES IN (357),
PARTITION `p358` VALUES IN (358),
PARTITION `p359` VALUES IN (359),
PARTITION `p360` VALUES IN (360),
PARTITION `p361` VALUES IN (361),
PARTITION `p362` VALUES IN (362),
PARTITION `p363` VALUES IN (363),
PARTITION `p364` VALUES IN (364),
PARTITION `p365` VALUES IN (365),
PARTITION `p366` VALUES IN (366),
PARTITION `p367` VALUES IN (367),
PARTITION `p368` VALUES IN (368),
PARTITION `p369` VALUES IN (369),
PARTITION `p370` VALUES IN (370),
PARTITION `p371` VALUES IN (371),
PARTITION `p372` VALUES IN (372),
PARTITION `p373` VALUES IN (373),
PARTITION `p374` VALUES IN (374),
PARTITION `p375` VALUES IN (375),
PARTITION `p376` VALUES IN (376),
PARTITION `p377` VALUES IN (377),
PARTITION `p378` VALUES IN (378),
PARTITION `p379` VALUES IN (379),
PARTITION `p380` VALUES IN (380),
PARTITION `p381` VALUES IN (381),
PARTITION `p382` VALUES IN (382),
PARTITION `p383` VALUES IN (383),
PARTITION `p384` VALUES IN (384),
PARTITION `p385` VALUES IN (385),
PARTITION `p386` VALUES IN (386),
PARTITION `p387` VALUES IN (387),
PARTITION `p388` VALUES IN (388),
PARTITION `p389` VALUES IN (389),
PARTITION `p390` VALUES IN (390),
PARTITION `p391` VALUES IN (391),
PARTITION `p392` VALUES IN (392),
PARTITION `p393` VALUES IN (393),
PARTITION `p394` VALUES IN (394),
PARTITION `p395` VALUES IN (395),
PARTITION `p396` VALUES IN (396),
PARTITION `p397` VALUES IN (397),
PARTITION `p398` VALUES IN (398),
PARTITION `p399` VALUES IN (399),
PARTITION `p400` VALUES IN (400),
PARTITION `p401` VALUES IN (401),
PARTITION `p402` VALUES IN (402),
PARTITION `p403` VALUES IN (403),
PARTITION `p404` VALUES IN (404),
PARTITION `p405` VALUES IN (405),
PARTITION `p406` VALUES IN (406),
PARTITION `p407` VALUES IN (407),
PARTITION `p408` VALUES IN (408),
PARTITION `p409` VALUES IN (409),
PARTITION `p410` VALUES IN (410),
PARTITION `p411` VALUES IN (411),
PARTITION `p412` VALUES IN (412),
PARTITION `p413` VALUES IN (413),
PARTITION `p414` VALUES IN (414),
PARTITION `p415` VALUES IN (415),
PARTITION `p416` VALUES IN (416),
PARTITION `p417` VALUES IN (417),
PARTITION `p418` VALUES IN (418),
PARTITION `p419` VALUES IN (419),
PARTITION `p420` VALUES IN (420),
PARTITION `p421` VALUES IN (421),
PARTITION `p422` VALUES IN (422),
PARTITION `p423` VALUES IN (423),
PARTITION `p424` VALUES IN (424),
PARTITION `p425` VALUES IN (425),
PARTITION `p426` VALUES IN (426),
PARTITION `p427` VALUES IN (427),
PARTITION `p428` VALUES IN (428),
PARTITION `p429` VALUES IN (429),
PARTITION `p430` VALUES IN (430),
PARTITION `p431` VALUES IN (431),
PARTITION `p432` VALUES IN (432),
PARTITION `p433` VALUES IN (433),
PARTITION `p434` VALUES IN (434),
PARTITION `p435` VALUES IN (435),
PARTITION `p436` VALUES IN (436),
PARTITION `p437` VALUES IN (437),
PARTITION `p438` VALUES IN (438),
PARTITION `p439` VALUES IN (439),
PARTITION `p440` VALUES IN (440),
PARTITION `p441` VALUES IN (441),
PARTITION `p442` VALUES IN (442),
PARTITION `p443` VALUES IN (443),
PARTITION `p444` VALUES IN (444),
PARTITION `p445` VALUES IN (445),
PARTITION `p446` VALUES IN (446),
PARTITION `p447` VALUES IN (447),
PARTITION `p448` VALUES IN (448),
PARTITION `p449` VALUES IN (449),
PARTITION `p450` VALUES IN (450),
PARTITION `p451` VALUES IN (451),
PARTITION `p452` VALUES IN (452),
PARTITION `p453` VALUES IN (453),
PARTITION `p454` VALUES IN (454),
PARTITION `p455` VALUES IN (455),
PARTITION `p456` VALUES IN (456),
PARTITION `p457` VALUES IN (457),
PARTITION `p458` VALUES IN (458),
PARTITION `p459` VALUES IN (459),
PARTITION `p460` VALUES IN (460),
PARTITION `p461` VALUES IN (461),
PARTITION `p462` VALUES IN (462),
PARTITION `p463` VALUES IN (463),
PARTITION `p464` VALUES IN (464),
PARTITION `p465` VALUES IN (465),
PARTITION `p466` VALUES IN (466),
PARTITION `p467` VALUES IN (467),
PARTITION `p468` VALUES IN (468),
PARTITION `p469` VALUES IN (469),
PARTITION `p470` VALUES IN (470),
PARTITION `p471` VALUES IN (471),
PARTITION `p472` VALUES IN (472),
PARTITION `p473` VALUES IN (473),
PARTITION `p474` VALUES IN (474),
PARTITION `p475` VALUES IN (475),
PARTITION `p476` VALUES IN (476),
PARTITION `p477` VALUES IN (477),
PARTITION `p478` VALUES IN (478),
PARTITION `p479` VALUES IN (479),
PARTITION `p480` VALUES IN (480),
PARTITION `p481` VALUES IN (481),
PARTITION `p482` VALUES IN (482),
PARTITION `p483` VALUES IN (483),
PARTITION `p484` VALUES IN (484),
PARTITION `p485` VALUES IN (485),
PARTITION `p486` VALUES IN (486),
PARTITION `p487` VALUES IN (487),
PARTITION `p488` VALUES IN (488),
PARTITION `p489` VALUES IN (489),
PARTITION `p490` VALUES IN (490),
PARTITION `p491` VALUES IN (491),
PARTITION `p492` VALUES IN (492),
PARTITION `p493` VALUES IN (493),
PARTITION `p494` VALUES IN (494),
PARTITION `p495` VALUES IN (495),
PARTITION `p496` VALUES IN (496),
PARTITION `p497` VALUES IN (497),
PARTITION `p498` VALUES IN (498),
PARTITION `p499` VALUES IN (499),
PARTITION `p500` VALUES IN (500),
PARTITION `p501` VALUES IN (501),
PARTITION `p502` VALUES IN (502),
PARTITION `p503` VALUES IN (503),
PARTITION `p504` VALUES IN (504),
PARTITION `p505` VALUES IN (505),
PARTITION `p506` VALUES IN (506),
PARTITION `p507` VALUES IN (507),
PARTITION `p508` VALUES IN (508),
PARTITION `p509` VALUES IN (509),
PARTITION `p510` VALUES IN (510),
PARTITION `p511` VALUES IN (511),
PARTITION `p512` VALUES IN (512),
PARTITION `p513` VALUES IN (513),
PARTITION `p514` VALUES IN (514),
PARTITION `p515` VALUES IN (515),
PARTITION `p516` VALUES IN (516),
PARTITION `p517` VALUES IN (517),
PARTITION `p518` VALUES IN (518),
PARTITION `p519` VALUES IN (519),
PARTITION `p520` VALUES IN (520),
PARTITION `p521` VALUES IN (521),
PARTITION `p522` VALUES IN (522),
PARTITION `p523` VALUES IN (523),
PARTITION `p524` VALUES IN (524),
PARTITION `p525` VALUES IN (525),
PARTITION `p526` VALUES IN (526),
PARTITION `p527` VALUES IN (527),
PARTITION `p528` VALUES IN (528),
PARTITION `p529` VALUES IN (529),
PARTITION `p530` VALUES IN (530),
PARTITION `p531` VALUES IN (531),
PARTITION `p532` VALUES IN (532),
PARTITION `p533` VALUES IN (533),
PARTITION `p534` VALUES IN (534),
PARTITION `p535` VALUES IN (535),
PARTITION `p536` VALUES IN (536),
PARTITION `p537` VALUES IN (537),
PARTITION `p538` VALUES IN (538),
PARTITION `p539` VALUES IN (539),
PARTITION `p540` VALUES IN (540),
PARTITION `p541` VALUES IN (541),
PARTITION `p542` VALUES IN (542),
PARTITION `p543` VALUES IN (543),
PARTITION `p544` VALUES IN (544),
PARTITION `p545` VALUES IN (545),
PARTITION `p546` VALUES IN (546),
PARTITION `p547` VALUES IN (547),
PARTITION `p548` VALUES IN (548),
PARTITION `p549` VALUES IN (549),
PARTITION `p550` VALUES IN (550),
PARTITION `p551` VALUES IN (551),
PARTITION `p552` VALUES IN (552),
PARTITION `p553` VALUES IN (553),
PARTITION `p554` VALUES IN (554),
PARTITION `p555` VALUES IN (555),
PARTITION `p556` VALUES IN (556),
PARTITION `p557` VALUES IN (557),
PARTITION `p558` VALUES IN (558),
PARTITION `p559` VALUES IN (559),
PARTITION `p560` VALUES IN (560),
PARTITION `p561` VALUES IN (561),
PARTITION `p562` VALUES IN (562),
PARTITION `p563` VALUES IN (563),
PARTITION `p564` VALUES IN (564),
PARTITION `p565` VALUES IN (565),
PARTITION `p566` VALUES IN (566),
PARTITION `p567` VALUES IN (567),
PARTITION `p568` VALUES IN (568),
PARTITION `p569` VALUES IN (569),
PARTITION `p570` VALUES IN (570),
PARTITION `p571` VALUES IN (571),
PARTITION `p572` VALUES IN (572),
PARTITION `p573` VALUES IN (573),
PARTITION `p574` VALUES IN (574),
PARTITION `p575` VALUES IN (575),
PARTITION `p576` VALUES IN (576),
PARTITION `p577` VALUES IN (577),
PARTITION `p578` VALUES IN (578),
PARTITION `p579` VALUES IN (579),
PARTITION `p580` VALUES IN (580),
PARTITION `p581` VALUES IN (581),
PARTITION `p582` VALUES IN (582),
PARTITION `p583` VALUES IN (583),
PARTITION `p584` VALUES IN (584),
PARTITION `p585` VALUES IN (585),
PARTITION `p586` VALUES IN (586),
PARTITION `p587` VALUES IN (587),
PARTITION `p588` VALUES IN (588),
PARTITION `p589` VALUES IN (589),
PARTITION `p590` VALUES IN (590),
PARTITION `p591` VALUES IN (591),
PARTITION `p592` VALUES IN (592),
PARTITION `p593` VALUES IN (593),
PARTITION `p594` VALUES IN (594),
PARTITION `p595` VALUES IN (595),
PARTITION `p596` VALUES IN (596),
PARTITION `p597` VALUES IN (597),
PARTITION `p598` VALUES IN (598),
PARTITION `p599` VALUES IN (599)

        )
    DISTRIBUTED BY HASH(`id`) BUCKETS 1
    PROPERTIES
    (
        "replication_num" = "1"
    );

    """

    sql """
        INSERT INTO test_600_partition_table_analyze VALUES (0),(1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),(33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(52),(53),(54),(55),(56),(57),(58),(59),(60),(61),(62),(63),(64),(65),(66),(67),(68),(69),(70),(71),(72),(73),(74),(75),(76),(77),(78),(79),(80),(81),(82),(83),(84),(85),(86),(87),(88),(89),(90),(91),(92),(93),(94),(95),(96),(97),(98),(99),(100),(101),(102),(103),(104),(105),(106),(107),(108),(109),(110),(111),(112),(113),(114),(115),(116),(117),(118),(119),(120),(121),(122),(123),(124),(125),(126),(127),(128),(129),(130),(131),(132),(133),(134),(135),(136),(137),(138),(139),(140),(141),(142),(143),(144),(145),(146),(147),(148),(149),(150),(151),(152),(153),(154),(155),(156),(157),(158),(159),(160),(161),(162),(163),(164),(165),(166),(167),(168),(169),(170),(171),(172),(173),(174),(175),(176),(177),(178),(179),(180),(181),(182),(183),(184),(185),(186),(187),(188),(189),(190),(191),(192),(193),(194),(195),(196),(197),(198),(199),(200),(201),(202),(203),(204),(205),(206),(207),(208),(209),(210),(211),(212),(213),(214),(215),(216),(217),(218),(219),(220),(221),(222),(223),(224),(225),(226),(227),(228),(229),(230),(231),(232),(233),(234),(235),(236),(237),(238),(239),(240),(241),(242),(243),(244),(245),(246),(247),(248),(249),(250),(251),(252),(253),(254),(255),(256),(257),(258),(259),(260),(261),(262),(263),(264),(265),(266),(267),(268),(269),(270),(271),(272),(273),(274),(275),(276),(277),(278),(279),(280),(281),(282),(283),(284),(285),(286),(287),(288),(289),(290),(291),(292),(293),(294),(295),(296),(297),(298),(299),(300)
    """
    sql """
        INSERT INTO test_600_partition_table_analyze VALUES (301),(302),(303),(304),(305),(306),(307),(308),(309),(310),(311),(312),(313),(314),(315),(316),(317),(318),(319),(320),(321),(322),(323),(324),(325),(326),(327),(328),(329),(330),(331),(332),(333),(334),(335),(336),(337),(338),(339),(340),(341),(342),(343),(344),(345),(346),(347),(348),(349),(350),(351),(352),(353),(354),(355),(356),(357),(358),(359),(360),(361),(362),(363),(364),(365),(366),(367),(368),(369),(370),(371),(372),(373),(374),(375),(376),(377),(378),(379),(380),(381),(382),(383),(384),(385),(386),(387),(388),(389),(390),(391),(392),(393),(394),(395),(396),(397),(398),(399),(400),(401),(402),(403),(404),(405),(406),(407),(408),(409),(410),(411),(412),(413),(414),(415),(416),(417),(418),(419),(420),(421),(422),(423),(424),(425),(426),(427),(428),(429),(430),(431),(432),(433),(434),(435),(436),(437),(438),(439),(440),(441),(442),(443),(444),(445),(446),(447),(448),(449),(450),(451),(452),(453),(454),(455),(456),(457),(458),(459),(460),(461),(462),(463),(464),(465),(466),(467),(468),(469),(470),(471),(472),(473),(474),(475),(476),(477),(478),(479),(480),(481),(482),(483),(484),(485),(486),(487),(488),(489),(490),(491),(492),(493),(494),(495),(496),(497),(498),(499),(500),(501),(502),(503),(504),(505),(506),(507),(508),(509),(510),(511),(512),(513),(514),(515),(516),(517),(518),(519),(520),(521),(522),(523),(524),(525),(526),(527),(528),(529),(530),(531),(532),(533),(534),(535),(536),(537),(538),(539),(540),(541),(542),(543),(544),(545),(546),(547),(548),(549),(550),(551),(552),(553),(554),(555),(556),(557),(558),(559),(560),(561),(562),(563),(564),(565),(566),(567),(568),(569),(570),(571),(572),(573),(574),(575),(576),(577),(578),(579),(580),(581),(582),(583),(584),(585),(586),(587),(588),(589),(590),(591),(592),(593),(594),(595),(596),(597),(598),(599)
    """

    sql """ANALYZE TABLE test_600_partition_table_analyze WITH SYNC"""

    //  0:column_name | 1:index_name | 2:count | 3:ndv  | 4:num_null | 5:data_size | 6:avg_size_byte | 7:min  | 8:max  | 9:method | 10:type | 11:trigger | 12:query_times | 13:updated_time
    def id_col_stats = sql """
        SHOW COLUMN CACHED STATS test_600_partition_table_analyze(id);
    """

    def expected_col_stats = { r, expected_value, idx ->
        return (int) Double.parseDouble(r[0][idx]) == expected_value
    }

    assert expected_col_stats(id_col_stats, 600, 2)
    assert (int) Double.parseDouble(id_col_stats[0][3]) < 700
            && (int) Double.parseDouble(id_col_stats[0][3]) > 500
    assert expected_col_stats(id_col_stats, 0, 4)
    assert expected_col_stats(id_col_stats, 2400, 5)
    assert expected_col_stats(id_col_stats, 4, 6)
    assert expected_col_stats(id_col_stats, 0, 7)
    assert expected_col_stats(id_col_stats, 599, 8)

    sql """DROP TABLE IF EXISTS increment_analyze_test"""
    sql """
        CREATE TABLE increment_analyze_test (
            id BIGINT
        ) DUPLICATE KEY(`id`)
        PARTITION BY RANGE(`id`)
        (
            PARTITION `p1` VALUES LESS THAN ('5')
        )

        DISTRIBUTED BY HASH(`id`) BUCKETS 3
        PROPERTIES (
            "replication_num"="1"
        );
    """

    sql """INSERT INTO increment_analyze_test VALUES(1),(2),(3)"""
    sql """ANALYZE TABLE increment_analyze_test WITH SYNC"""
    sql """ALTER TABLE increment_analyze_test ADD PARTITION p2 VALUES LESS THAN('10')"""

    sql """INSERT INTO increment_analyze_test VALUES(6),(7),(8)"""
    sql """ANALYZE TABLE increment_analyze_test WITH SYNC WITH INCREMENTAL"""
    def inc_res = sql """
        SHOW COLUMN CACHED STATS increment_analyze_test(id)
    """

    expected_col_stats(inc_res, 6, 2)

    sql """
        DROP TABLE increment_analyze_test;
    """

    sql """
        CREATE TABLE a_partitioned_table_for_analyze_test (
            id BIGINT,
            val BIGINT,
            str VARCHAR(114)
        ) DUPLICATE KEY(`id`)
        PARTITION BY RANGE(`id`)
        (
            PARTITION `p1` VALUES LESS THAN ('5'),
            PARTITION `p2` VALUES LESS THAN ('10'),
            PARTITION `p3` VALUES LESS THAN ('15')
        )
        DISTRIBUTED BY HASH(`id`) BUCKETS 3
        PROPERTIES (
        "replication_num"="1"
        );
    """

    sql """
        INSERT INTO a_partitioned_table_for_analyze_test VALUES(1, 5, 11),(6,1,5),(11,8,5);
    """

    sql """
        ANALYZE TABLE a_partitioned_table_for_analyze_test(id) WITH SYNC
    """

    sql """
        ANALYZE TABLE a_partitioned_table_for_analyze_test(val) WITH SYNC
    """

    def col_val_res = sql """
        SHOW COLUMN CACHED STATS a_partitioned_table_for_analyze_test(val)
    """

    expected_col_stats(col_val_res, 3, 2)

    def col_id_res = sql """
        SHOW COLUMN CACHED STATS a_partitioned_table_for_analyze_test(id)
    """
    expected_col_stats(col_id_res, 3, 2)

    sql """DROP TABLE IF EXISTS `some_complex_type_test`"""

    sql """
       CREATE TABLE `some_complex_type_test` (
          `id` int(11) NULL COMMENT "",
          `c_array` ARRAY<int(11)> NULL COMMENT ""
        ) ENGINE=OLAP
        DUPLICATE KEY(`id`)
        COMMENT "OLAP"
        DISTRIBUTED BY HASH(`id`) BUCKETS 1
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "in_memory" = "false",
        "storage_format" = "V2"
        );
    """

    sql """INSERT INTO `some_complex_type_test` VALUES (1, [1,2,3,4,5]);"""
    sql """INSERT INTO `some_complex_type_test` VALUES (2, [6,7,8]), (3, []), (4, null);"""

    sql """
        ANALYZE TABLE `some_complex_type_test` WITH SYNC;

    """

    sql """
        SELECT COUNT(1) FROM `some_complex_type_test`
    """

    sql """DROP TABLE IF EXISTS `analyze_test_with_schema_update`"""

    sql """
        CREATE TABLE `analyze_test_with_schema_update` (
        col1 varchar(11451) not null, col2 int not null, col3 int not null)
        DUPLICATE KEY(col1)
        DISTRIBUTED BY HASH(col1)
        BUCKETS 3
        PROPERTIES(
            "replication_num"="1"
        );
    """

    sql """insert into analyze_test_with_schema_update values(1, 2, 3);"""
    sql """insert into analyze_test_with_schema_update values(4, 5, 6);"""
    sql """insert into analyze_test_with_schema_update values(7, 1, 9);"""
    sql """insert into analyze_test_with_schema_update values(3, 8, 2);"""
    sql """insert into analyze_test_with_schema_update values(5, 2, 1);"""

    sql """
        ANALYZE TABLE analyze_test_with_schema_update WITH SYNC
    """

    sql """
        ALTER TABLE analyze_test_with_schema_update ADD COLUMN tbl_name VARCHAR(256) DEFAULT NULL;
    """

    stats_dropped("analyze_test_with_schema_update")

    sql """
        ANALYZE TABLE analyze_test_with_schema_update WITH SYNC
    """

    sql """
        SELECT * FROM analyze_test_with_schema_update;
    """

    sql """
        DROP STATS analyze_test_with_schema_update(col3);
    """

    sql """
        ANALYZE TABLE analyze_test_with_schema_update WITH SYNC
    """

    sql """
        SELECT * FROM analyze_test_with_schema_update;
    """

    sql """
        DROP TABLE IF EXISTS two_thousand_partition_table_test
    """

    // check analyze table with thousand partition
    sql """
        CREATE TABLE two_thousand_partition_table_test (col1 int(11451) not null)
        DUPLICATE KEY(col1)
          PARTITION BY RANGE(`col1`)
                  (
                  from (0) to (1000001) INTERVAL 500
                  )
        DISTRIBUTED BY HASH(col1)
        BUCKETS 1
        PROPERTIES(
            "replication_num"="1"
        );
    """

    sql """
        ANALYZE TABLE two_thousand_partition_table_test WITH SYNC;
    """

    // meta check
    sql """
        CREATE TABLE `test_meta_management` (
           `col1` varchar(11451) NOT NULL,
           `col2` int(11) NOT NULL,
           `col3` int(11) NOT NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`col1`)
        COMMENT 'OLAP'
        DISTRIBUTED BY HASH(`col1`) BUCKETS 3
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1"
        );
    """

    sql """insert into test_meta_management values(1, 2, 3);"""
    sql """insert into test_meta_management values(4, 5, 6);"""
    sql """insert into test_meta_management values(7, 1, 9);"""
    sql """insert into test_meta_management values(3, 8, 2);"""
    sql """insert into test_meta_management values(5, 2, 1);"""
    sql """insert into test_meta_management values(41, 2, 3)"""

    sql """ANALYZE TABLE test_meta_management WITH SYNC"""
    sql """DROP STATS test_meta_management(col1)"""

    def afterDropped = sql """SHOW TABLE STATS test_meta_management"""
    def convert_col_list_str_to_java_collection = { cols ->
        if (cols.startsWith("[") && cols.endsWith("]")) {
            cols = cols.substring(1, cols.length() - 1);
        }
        return Arrays.stream(cols.split(",")).map(String::trim).collect(Collectors.toList())
    }

    def check_column = { r, expected ->
        expected_result = convert_col_list_str_to_java_collection(expected)
        def actual_result = convert_col_list_str_to_java_collection(r[0][4])
        System.out.println(expected_result)
        System.out.println(actual_result)
        return expected_result.containsAll(actual_result) && actual_result.containsAll(expected_result)
    }
    assert check_column(afterDropped, "[test_meta_management:col2, test_meta_management:col3]")
    sql """ANALYZE TABLE test_meta_management WITH SYNC"""
    afterDropped = sql """SHOW TABLE STATS test_meta_management"""
    assert check_column(afterDropped, "[test_meta_management:col1, test_meta_management:col2, test_meta_management:col3]")

    sql """ DROP TABLE IF EXISTS test_updated_rows """
    sql """
        CREATE TABLE test_updated_rows (
            `col1` varchar(16) NOT NULL,
            `col2` int(11) NOT NULL,
            `col3` int(11) NOT NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`col1`)
        DISTRIBUTED BY HASH(`col1`) BUCKETS 3
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1",
        "storage_format" = "V2",
        "light_schema_change" = "true",
        "disable_auto_compaction" = "false",
        "enable_single_replica_compaction" = "false"
        );
    """

    sql """ANALYZE TABLE test_updated_rows WITH SYNC"""
    sql """ INSERT INTO test_updated_rows VALUES('1',1,1); """
    def cnt1 = sql """ SHOW TABLE STATS test_updated_rows """
    assertEquals(Integer.valueOf(cnt1[0][0]), 1)
    sql """ANALYZE TABLE test_updated_rows WITH SYNC"""
    sql """ INSERT INTO test_updated_rows SELECT * FROM test_updated_rows """
    sql """ INSERT INTO test_updated_rows SELECT * FROM test_updated_rows """
    sql """ INSERT INTO test_updated_rows SELECT * FROM test_updated_rows """
    sql """ANALYZE TABLE test_updated_rows WITH SYNC"""
    def cnt2 = sql """ SHOW TABLE STATS test_updated_rows """
    assertEquals(Integer.valueOf(cnt2[0][0]), 8)

    // test analyze specific column
    sql """CREATE TABLE test_analyze_specific_column (col1 varchar(11451) not null, col2 int not null, col3 int not null)
    DUPLICATE KEY(col1)
    DISTRIBUTED BY HASH(col1)
    BUCKETS 3
    PROPERTIES(
            "replication_num"="1"
    );"""
    sql """insert into test_analyze_specific_column values('%.', 2, 1);"""
    sql """ANALYZE TABLE test_analyze_specific_column(col2) WITH SYNC"""
    def result = sql """SHOW COLUMN STATS test_analyze_specific_column"""
    assert result.size() == 1

    // test escape sql
    sql """
         DROP TABLE IF EXISTS test_max_min_lit;
    """

    sql """
          CREATE TABLE test_max_min_lit  (
          `col1` varchar(32) NULL
          ) ENGINE=OLAP
            DUPLICATE KEY(`col1`)
            COMMENT 'OLAP'
            DISTRIBUTED BY HASH(`col1`) BUCKETS 3
            PROPERTIES (
            "replication_allocation" = "tag.location.default: 1"
            );
    """

    sql """INSERT INTO test_max_min_lit VALUES("\\'")"""
    sql """INSERT INTO test_max_min_lit VALUES('\\';')"""
    sql "INSERT INTO test_max_min_lit VALUES('测试')"

    sql """ANALYZE TABLE test_max_min_lit WITH SYNC"""
    def max = sql """show column cached stats test_max_min_lit"""
    def expected_max = { r, expected_value ->
        return (r[0][7]).equals(expected_value)
    }
    expected_max(max, "测试")

    show_result = sql """
        SHOW ANALYZE ${tbl}
    """

    def tbl_name_as_expetected = { r,name ->
        for (int i = 0; i < r.size(); i++) {
            if (r[i][3] != name) {
                return false
            }
        }
        return true
    }

    assert show_result[0][9] == "FINISHED"
    assert tbl_name_as_expetected(show_result, "${tbl}")

    show_result = sql """
        SHOW ANALYZE ${tbl} WHERE STATE = "FINISHED"
    """

    assert show_result.size() > 0

    def all_finished = { r ->
        for (int i = 0; i < r.size(); i++) {
            if (r[i][9] != "FINISHED") {
                return  false
            }
        }
        return true
    }

    assert all_finished(show_result)

    // Test truncate table will drop table stats too.
    sql """ANALYZE TABLE ${tbl} WITH SYNC"""
    def result_before_truncate = sql """show column stats ${tbl}"""
    assertEquals(14, result_before_truncate.size())
    sql """TRUNCATE TABLE ${tbl}"""
    stats_dropped(tbl)
    def result_after_truncate = sql """show column stats ${tbl}"""
    assertEquals(0, result_after_truncate.size())
    result_after_truncate = sql """show column cached stats ${tbl}"""
    assertEquals(0, result_after_truncate.size())

    sql """
        delete from ${tbl} where analyzetestlimitedk3 >= -2147483648
    """
    sql """
        INSERT INTO `${tbl}` VALUES (-2103297891,1,101,15248,4761818404925265645,939926.283,
        'UTmCFKMbprf0zSVOIlBJRNOl3JcNBdOsnCDt','2022-09-28','2022-10-28 01:56:56','tVvGDSrN6kyn',
        -954349107.187117,-40.46286,'1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111g1ZP9nqVgaGKya3kPERdBofTWJQ4TIJEz972Xvw4hfPpTpWwlmondiLVTCyld7rSBlSWrE7NJRB0pvPGEFQKOx1s3',
        '-1559301292834325905', NULL, NULL, NULL, NULL)
    """

    sql """
        ANALYZE TABLE ${tbl} WITH SYNC
    """

    def truncate_test_result = sql """
        SHOW COLUMN CACHED STATS ${tbl}(analyzetestlimitedk12)
    """
    assert "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" == truncate_test_result[0][7].substring(1, 1025)
    assert "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" == truncate_test_result[0][8].substring(1, 1025)

    sql """TRUNCATE TABLE ${tbl}"""
    stats_dropped(tbl)
    result_after_truncate = sql """show column stats ${tbl}"""
    assertEquals(0, result_after_truncate.size())
    sql """ANALYZE TABLE ${tbl} WITH SYNC"""
    result_after_truncate = sql """show column stats ${tbl}"""
    assertEquals(14, result_after_truncate.size())

    result = sql """show column stats ${tbl}(analyzetestlimitedk0);"""
    logger.info("show column analyzetestlimitedk0 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk0", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk1);"""
    logger.info("show column analyzetestlimitedk1 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk1", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk2);"""
    logger.info("show column analyzetestlimitedk2 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk2", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk3);"""
    logger.info("show column analyzetestlimitedk3 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk3", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk4);"""
    logger.info("show column analyzetestlimitedk4 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk4", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk5);"""
    logger.info("show column analyzetestlimitedk5 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk5", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk6);"""
    logger.info("show column analyzetestlimitedk6 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk6", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk7);"""
    logger.info("show column analyzetestlimitedk7 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk7", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk8);"""
    logger.info("show column analyzetestlimitedk8 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk8", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk9);"""
    logger.info("show column analyzetestlimitedk9 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk9", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk10);"""
    logger.info("show column analyzetestlimitedk10 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk10", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk11);"""
    logger.info("show column analyzetestlimitedk11 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk11", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk12);"""
    logger.info("show column analyzetestlimitedk12 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk12", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    result = sql """show column stats ${tbl}(analyzetestlimitedk13);"""
    logger.info("show column analyzetestlimitedk13 stats: " + result)
    assertEquals(1, result.size())
    assertEquals("analyzetestlimitedk13", result[0][0])
    assertEquals("0.0", result[0][2])
    assertEquals("0.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("0.0", result[0][5])
    assertEquals("0.0", result[0][6])
    assertEquals("N/A", result[0][7])
    assertEquals("N/A", result[0][8])

    // Test drop stats with more than 1024 columns.
    sql """
        CREATE TABLE if not exists col1100 (
            c1 int,
            c2 int,
            c3 int,
            c4 int,
            c5 int,
            c6 int,
            c7 int,
            c8 int,
            c9 int,
            c10 int,
            c11 int,
            c12 int,
            c13 int,
            c14 int,
            c15 int,
            c16 int,
            c17 int,
            c18 int,
            c19 int,
            c20 int,
            c21 int,
            c22 int,
            c23 int,
            c24 int,
            c25 int,
            c26 int,
            c27 int,
            c28 int,
            c29 int,
            c30 int,
            c31 int,
            c32 int,
            c33 int,
            c34 int,
            c35 int,
            c36 int,
            c37 int,
            c38 int,
            c39 int,
            c40 int,
            c41 int,
            c42 int,
            c43 int,
            c44 int,
            c45 int,
            c46 int,
            c47 int,
            c48 int,
            c49 int,
            c50 int,
            c51 int,
            c52 int,
            c53 int,
            c54 int,
            c55 int,
            c56 int,
            c57 int,
            c58 int,
            c59 int,
            c60 int,
            c61 int,
            c62 int,
            c63 int,
            c64 int,
            c65 int,
            c66 int,
            c67 int,
            c68 int,
            c69 int,
            c70 int,
            c71 int,
            c72 int,
            c73 int,
            c74 int,
            c75 int,
            c76 int,
            c77 int,
            c78 int,
            c79 int,
            c80 int,
            c81 int,
            c82 int,
            c83 int,
            c84 int,
            c85 int,
            c86 int,
            c87 int,
            c88 int,
            c89 int,
            c90 int,
            c91 int,
            c92 int,
            c93 int,
            c94 int,
            c95 int,
            c96 int,
            c97 int,
            c98 int,
            c99 int,
            c100 int,
            c101 int,
            c102 int,
            c103 int,
            c104 int,
            c105 int,
            c106 int,
            c107 int,
            c108 int,
            c109 int,
            c110 int,
            c111 int,
            c112 int,
            c113 int,
            c114 int,
            c115 int,
            c116 int,
            c117 int,
            c118 int,
            c119 int,
            c120 int,
            c121 int,
            c122 int,
            c123 int,
            c124 int,
            c125 int,
            c126 int,
            c127 int,
            c128 int,
            c129 int,
            c130 int,
            c131 int,
            c132 int,
            c133 int,
            c134 int,
            c135 int,
            c136 int,
            c137 int,
            c138 int,
            c139 int,
            c140 int,
            c141 int,
            c142 int,
            c143 int,
            c144 int,
            c145 int,
            c146 int,
            c147 int,
            c148 int,
            c149 int,
            c150 int,
            c151 int,
            c152 int,
            c153 int,
            c154 int,
            c155 int,
            c156 int,
            c157 int,
            c158 int,
            c159 int,
            c160 int,
            c161 int,
            c162 int,
            c163 int,
            c164 int,
            c165 int,
            c166 int,
            c167 int,
            c168 int,
            c169 int,
            c170 int,
            c171 int,
            c172 int,
            c173 int,
            c174 int,
            c175 int,
            c176 int,
            c177 int,
            c178 int,
            c179 int,
            c180 int,
            c181 int,
            c182 int,
            c183 int,
            c184 int,
            c185 int,
            c186 int,
            c187 int,
            c188 int,
            c189 int,
            c190 int,
            c191 int,
            c192 int,
            c193 int,
            c194 int,
            c195 int,
            c196 int,
            c197 int,
            c198 int,
            c199 int,
            c200 int,
            c201 int,
            c202 int,
            c203 int,
            c204 int,
            c205 int,
            c206 int,
            c207 int,
            c208 int,
            c209 int,
            c210 int,
            c211 int,
            c212 int,
            c213 int,
            c214 int,
            c215 int,
            c216 int,
            c217 int,
            c218 int,
            c219 int,
            c220 int,
            c221 int,
            c222 int,
            c223 int,
            c224 int,
            c225 int,
            c226 int,
            c227 int,
            c228 int,
            c229 int,
            c230 int,
            c231 int,
            c232 int,
            c233 int,
            c234 int,
            c235 int,
            c236 int,
            c237 int,
            c238 int,
            c239 int,
            c240 int,
            c241 int,
            c242 int,
            c243 int,
            c244 int,
            c245 int,
            c246 int,
            c247 int,
            c248 int,
            c249 int,
            c250 int,
            c251 int,
            c252 int,
            c253 int,
            c254 int,
            c255 int,
            c256 int,
            c257 int,
            c258 int,
            c259 int,
            c260 int,
            c261 int,
            c262 int,
            c263 int,
            c264 int,
            c265 int,
            c266 int,
            c267 int,
            c268 int,
            c269 int,
            c270 int,
            c271 int,
            c272 int,
            c273 int,
            c274 int,
            c275 int,
            c276 int,
            c277 int,
            c278 int,
            c279 int,
            c280 int,
            c281 int,
            c282 int,
            c283 int,
            c284 int,
            c285 int,
            c286 int,
            c287 int,
            c288 int,
            c289 int,
            c290 int,
            c291 int,
            c292 int,
            c293 int,
            c294 int,
            c295 int,
            c296 int,
            c297 int,
            c298 int,
            c299 int,
            c300 int,
            c301 int,
            c302 int,
            c303 int,
            c304 int,
            c305 int,
            c306 int,
            c307 int,
            c308 int,
            c309 int,
            c310 int,
            c311 int,
            c312 int,
            c313 int,
            c314 int,
            c315 int,
            c316 int,
            c317 int,
            c318 int,
            c319 int,
            c320 int,
            c321 int,
            c322 int,
            c323 int,
            c324 int,
            c325 int,
            c326 int,
            c327 int,
            c328 int,
            c329 int,
            c330 int,
            c331 int,
            c332 int,
            c333 int,
            c334 int,
            c335 int,
            c336 int,
            c337 int,
            c338 int,
            c339 int,
            c340 int,
            c341 int,
            c342 int,
            c343 int,
            c344 int,
            c345 int,
            c346 int,
            c347 int,
            c348 int,
            c349 int,
            c350 int,
            c351 int,
            c352 int,
            c353 int,
            c354 int,
            c355 int,
            c356 int,
            c357 int,
            c358 int,
            c359 int,
            c360 int,
            c361 int,
            c362 int,
            c363 int,
            c364 int,
            c365 int,
            c366 int,
            c367 int,
            c368 int,
            c369 int,
            c370 int,
            c371 int,
            c372 int,
            c373 int,
            c374 int,
            c375 int,
            c376 int,
            c377 int,
            c378 int,
            c379 int,
            c380 int,
            c381 int,
            c382 int,
            c383 int,
            c384 int,
            c385 int,
            c386 int,
            c387 int,
            c388 int,
            c389 int,
            c390 int,
            c391 int,
            c392 int,
            c393 int,
            c394 int,
            c395 int,
            c396 int,
            c397 int,
            c398 int,
            c399 int,
            c400 int,
            c401 int,
            c402 int,
            c403 int,
            c404 int,
            c405 int,
            c406 int,
            c407 int,
            c408 int,
            c409 int,
            c410 int,
            c411 int,
            c412 int,
            c413 int,
            c414 int,
            c415 int,
            c416 int,
            c417 int,
            c418 int,
            c419 int,
            c420 int,
            c421 int,
            c422 int,
            c423 int,
            c424 int,
            c425 int,
            c426 int,
            c427 int,
            c428 int,
            c429 int,
            c430 int,
            c431 int,
            c432 int,
            c433 int,
            c434 int,
            c435 int,
            c436 int,
            c437 int,
            c438 int,
            c439 int,
            c440 int,
            c441 int,
            c442 int,
            c443 int,
            c444 int,
            c445 int,
            c446 int,
            c447 int,
            c448 int,
            c449 int,
            c450 int,
            c451 int,
            c452 int,
            c453 int,
            c454 int,
            c455 int,
            c456 int,
            c457 int,
            c458 int,
            c459 int,
            c460 int,
            c461 int,
            c462 int,
            c463 int,
            c464 int,
            c465 int,
            c466 int,
            c467 int,
            c468 int,
            c469 int,
            c470 int,
            c471 int,
            c472 int,
            c473 int,
            c474 int,
            c475 int,
            c476 int,
            c477 int,
            c478 int,
            c479 int,
            c480 int,
            c481 int,
            c482 int,
            c483 int,
            c484 int,
            c485 int,
            c486 int,
            c487 int,
            c488 int,
            c489 int,
            c490 int,
            c491 int,
            c492 int,
            c493 int,
            c494 int,
            c495 int,
            c496 int,
            c497 int,
            c498 int,
            c499 int,
            c500 int,
            c501 int,
            c502 int,
            c503 int,
            c504 int,
            c505 int,
            c506 int,
            c507 int,
            c508 int,
            c509 int,
            c510 int,
            c511 int,
            c512 int,
            c513 int,
            c514 int,
            c515 int,
            c516 int,
            c517 int,
            c518 int,
            c519 int,
            c520 int,
            c521 int,
            c522 int,
            c523 int,
            c524 int,
            c525 int,
            c526 int,
            c527 int,
            c528 int,
            c529 int,
            c530 int,
            c531 int,
            c532 int,
            c533 int,
            c534 int,
            c535 int,
            c536 int,
            c537 int,
            c538 int,
            c539 int,
            c540 int,
            c541 int,
            c542 int,
            c543 int,
            c544 int,
            c545 int,
            c546 int,
            c547 int,
            c548 int,
            c549 int,
            c550 int,
            c551 int,
            c552 int,
            c553 int,
            c554 int,
            c555 int,
            c556 int,
            c557 int,
            c558 int,
            c559 int,
            c560 int,
            c561 int,
            c562 int,
            c563 int,
            c564 int,
            c565 int,
            c566 int,
            c567 int,
            c568 int,
            c569 int,
            c570 int,
            c571 int,
            c572 int,
            c573 int,
            c574 int,
            c575 int,
            c576 int,
            c577 int,
            c578 int,
            c579 int,
            c580 int,
            c581 int,
            c582 int,
            c583 int,
            c584 int,
            c585 int,
            c586 int,
            c587 int,
            c588 int,
            c589 int,
            c590 int,
            c591 int,
            c592 int,
            c593 int,
            c594 int,
            c595 int,
            c596 int,
            c597 int,
            c598 int,
            c599 int,
            c600 int,
            c601 int,
            c602 int,
            c603 int,
            c604 int,
            c605 int,
            c606 int,
            c607 int,
            c608 int,
            c609 int,
            c610 int,
            c611 int,
            c612 int,
            c613 int,
            c614 int,
            c615 int,
            c616 int,
            c617 int,
            c618 int,
            c619 int,
            c620 int,
            c621 int,
            c622 int,
            c623 int,
            c624 int,
            c625 int,
            c626 int,
            c627 int,
            c628 int,
            c629 int,
            c630 int,
            c631 int,
            c632 int,
            c633 int,
            c634 int,
            c635 int,
            c636 int,
            c637 int,
            c638 int,
            c639 int,
            c640 int,
            c641 int,
            c642 int,
            c643 int,
            c644 int,
            c645 int,
            c646 int,
            c647 int,
            c648 int,
            c649 int,
            c650 int,
            c651 int,
            c652 int,
            c653 int,
            c654 int,
            c655 int,
            c656 int,
            c657 int,
            c658 int,
            c659 int,
            c660 int,
            c661 int,
            c662 int,
            c663 int,
            c664 int,
            c665 int,
            c666 int,
            c667 int,
            c668 int,
            c669 int,
            c670 int,
            c671 int,
            c672 int,
            c673 int,
            c674 int,
            c675 int,
            c676 int,
            c677 int,
            c678 int,
            c679 int,
            c680 int,
            c681 int,
            c682 int,
            c683 int,
            c684 int,
            c685 int,
            c686 int,
            c687 int,
            c688 int,
            c689 int,
            c690 int,
            c691 int,
            c692 int,
            c693 int,
            c694 int,
            c695 int,
            c696 int,
            c697 int,
            c698 int,
            c699 int,
            c700 int,
            c701 int,
            c702 int,
            c703 int,
            c704 int,
            c705 int,
            c706 int,
            c707 int,
            c708 int,
            c709 int,
            c710 int,
            c711 int,
            c712 int,
            c713 int,
            c714 int,
            c715 int,
            c716 int,
            c717 int,
            c718 int,
            c719 int,
            c720 int,
            c721 int,
            c722 int,
            c723 int,
            c724 int,
            c725 int,
            c726 int,
            c727 int,
            c728 int,
            c729 int,
            c730 int,
            c731 int,
            c732 int,
            c733 int,
            c734 int,
            c735 int,
            c736 int,
            c737 int,
            c738 int,
            c739 int,
            c740 int,
            c741 int,
            c742 int,
            c743 int,
            c744 int,
            c745 int,
            c746 int,
            c747 int,
            c748 int,
            c749 int,
            c750 int,
            c751 int,
            c752 int,
            c753 int,
            c754 int,
            c755 int,
            c756 int,
            c757 int,
            c758 int,
            c759 int,
            c760 int,
            c761 int,
            c762 int,
            c763 int,
            c764 int,
            c765 int,
            c766 int,
            c767 int,
            c768 int,
            c769 int,
            c770 int,
            c771 int,
            c772 int,
            c773 int,
            c774 int,
            c775 int,
            c776 int,
            c777 int,
            c778 int,
            c779 int,
            c780 int,
            c781 int,
            c782 int,
            c783 int,
            c784 int,
            c785 int,
            c786 int,
            c787 int,
            c788 int,
            c789 int,
            c790 int,
            c791 int,
            c792 int,
            c793 int,
            c794 int,
            c795 int,
            c796 int,
            c797 int,
            c798 int,
            c799 int,
            c800 int,
            c801 int,
            c802 int,
            c803 int,
            c804 int,
            c805 int,
            c806 int,
            c807 int,
            c808 int,
            c809 int,
            c810 int,
            c811 int,
            c812 int,
            c813 int,
            c814 int,
            c815 int,
            c816 int,
            c817 int,
            c818 int,
            c819 int,
            c820 int,
            c821 int,
            c822 int,
            c823 int,
            c824 int,
            c825 int,
            c826 int,
            c827 int,
            c828 int,
            c829 int,
            c830 int,
            c831 int,
            c832 int,
            c833 int,
            c834 int,
            c835 int,
            c836 int,
            c837 int,
            c838 int,
            c839 int,
            c840 int,
            c841 int,
            c842 int,
            c843 int,
            c844 int,
            c845 int,
            c846 int,
            c847 int,
            c848 int,
            c849 int,
            c850 int,
            c851 int,
            c852 int,
            c853 int,
            c854 int,
            c855 int,
            c856 int,
            c857 int,
            c858 int,
            c859 int,
            c860 int,
            c861 int,
            c862 int,
            c863 int,
            c864 int,
            c865 int,
            c866 int,
            c867 int,
            c868 int,
            c869 int,
            c870 int,
            c871 int,
            c872 int,
            c873 int,
            c874 int,
            c875 int,
            c876 int,
            c877 int,
            c878 int,
            c879 int,
            c880 int,
            c881 int,
            c882 int,
            c883 int,
            c884 int,
            c885 int,
            c886 int,
            c887 int,
            c888 int,
            c889 int,
            c890 int,
            c891 int,
            c892 int,
            c893 int,
            c894 int,
            c895 int,
            c896 int,
            c897 int,
            c898 int,
            c899 int,
            c900 int,
            c901 int,
            c902 int,
            c903 int,
            c904 int,
            c905 int,
            c906 int,
            c907 int,
            c908 int,
            c909 int,
            c910 int,
            c911 int,
            c912 int,
            c913 int,
            c914 int,
            c915 int,
            c916 int,
            c917 int,
            c918 int,
            c919 int,
            c920 int,
            c921 int,
            c922 int,
            c923 int,
            c924 int,
            c925 int,
            c926 int,
            c927 int,
            c928 int,
            c929 int,
            c930 int,
            c931 int,
            c932 int,
            c933 int,
            c934 int,
            c935 int,
            c936 int,
            c937 int,
            c938 int,
            c939 int,
            c940 int,
            c941 int,
            c942 int,
            c943 int,
            c944 int,
            c945 int,
            c946 int,
            c947 int,
            c948 int,
            c949 int,
            c950 int,
            c951 int,
            c952 int,
            c953 int,
            c954 int,
            c955 int,
            c956 int,
            c957 int,
            c958 int,
            c959 int,
            c960 int,
            c961 int,
            c962 int,
            c963 int,
            c964 int,
            c965 int,
            c966 int,
            c967 int,
            c968 int,
            c969 int,
            c970 int,
            c971 int,
            c972 int,
            c973 int,
            c974 int,
            c975 int,
            c976 int,
            c977 int,
            c978 int,
            c979 int,
            c980 int,
            c981 int,
            c982 int,
            c983 int,
            c984 int,
            c985 int,
            c986 int,
            c987 int,
            c988 int,
            c989 int,
            c990 int,
            c991 int,
            c992 int,
            c993 int,
            c994 int,
            c995 int,
            c996 int,
            c997 int,
            c998 int,
            c999 int,
            c1000 int,
            c1001 int,
            c1002 int,
            c1003 int,
            c1004 int,
            c1005 int,
            c1006 int,
            c1007 int,
            c1008 int,
            c1009 int,
            c1010 int,
            c1011 int,
            c1012 int,
            c1013 int,
            c1014 int,
            c1015 int,
            c1016 int,
            c1017 int,
            c1018 int,
            c1019 int,
            c1020 int,
            c1021 int,
            c1022 int,
            c1023 int,
            c1024 int,
            c1025 int,
            c1026 int,
            c1027 int,
            c1028 int,
            c1029 int,
            c1030 int,
            c1031 int,
            c1032 int,
            c1033 int,
            c1034 int,
            c1035 int,
            c1036 int,
            c1037 int,
            c1038 int,
            c1039 int,
            c1040 int,
            c1041 int,
            c1042 int,
            c1043 int,
            c1044 int,
            c1045 int,
            c1046 int,
            c1047 int,
            c1048 int,
            c1049 int,
            c1050 int,
            c1051 int,
            c1052 int,
            c1053 int,
            c1054 int,
            c1055 int,
            c1056 int,
            c1057 int,
            c1058 int,
            c1059 int,
            c1060 int,
            c1061 int,
            c1062 int,
            c1063 int,
            c1064 int,
            c1065 int,
            c1066 int,
            c1067 int,
            c1068 int,
            c1069 int,
            c1070 int,
            c1071 int,
            c1072 int,
            c1073 int,
            c1074 int,
            c1075 int,
            c1076 int,
            c1077 int,
            c1078 int,
            c1079 int,
            c1080 int,
            c1081 int,
            c1082 int,
            c1083 int,
            c1084 int,
            c1085 int,
            c1086 int,
            c1087 int,
            c1088 int,
            c1089 int,
            c1090 int,
            c1091 int,
            c1092 int,
            c1093 int,
            c1094 int,
            c1095 int,
            c1096 int,
            c1097 int,
            c1098 int,
            c1099 int,
            c1100 int
        )ENGINE=OLAP
        DUPLICATE KEY(`c1`)
        COMMENT "OLAP"
        DISTRIBUTED BY HASH(`c1`) BUCKETS 1
        PROPERTIES (
        "replication_num" = "1"
        );
    """
    sql """drop stats col1100 """
    sql """DROP TABLE IF EXISTS col1100"""

    // Test analyze column with special character in name.
    sql """
      CREATE TABLE region  (
       `r_regionkey`      int NOT NULL,
       `r%name`     VARCHAR(25) NOT NULL,
       `r_comment`    VARCHAR(152)
      )ENGINE=OLAP
      DUPLICATE KEY(`r_regionkey`)
      COMMENT "OLAP"
      DISTRIBUTED BY HASH(`r_regionkey`) BUCKETS 1
      PROPERTIES (
       "replication_num" = "1"
      );
   """
    sql """insert into region values(1,'name1', 'comment1') """
    sql """insert into region values(2,'name2', 'comment2') """
    sql """insert into region values(3,'name3', 'comment3') """
    sql """ANALYZE TABLE region WITH SYNC"""
    result = sql """show column stats region (`r%name`);"""
    logger.info("show column r%name stats: " + result)
    assertEquals(1, result.size())
    assertEquals("r%name", result[0][0])
    assertEquals("3.0", result[0][2])
    assertEquals("3.0", result[0][3])
    assertEquals("0.0", result[0][4])
    assertEquals("\'name1\'", result[0][7])
    assertEquals("\'name3\'", result[0][8])

    // Test show analyze
    sql """drop stats region"""
    sql """analyze table region"""
    result = sql """show analyze region"""
    assertEquals(1, result.size())
    result = sql """show auto analyze region"""
    assertEquals(0, result.size())
    for (int i = 0; i < 100; i++) {
        result = sql """show analyze region"""
        if (!result[0][9].equals("FINISHED")) {
            Thread.sleep(1000)
            continue;
        }
        break;
    }
    assertEquals("FINISHED", result[0][9])
    assertEquals(1, result.size())
    result = sql """show analyze region where state="FINISHED";"""
    assertEquals(1, result.size())
    result = sql """show analyze region where state="pending";"""
    assertEquals(0, result.size())


    // Test sample string type min max
    sql """
     CREATE TABLE `string_min_max` (
      `id` BIGINT NOT NULL,
      `name` string NULL
     ) ENGINE=OLAP
     DUPLICATE KEY(`id`)
     COMMENT 'OLAP'
     DISTRIBUTED BY HASH(`id`) BUCKETS 32
     PROPERTIES (
      "replication_num" = "1"
     );
   """
    sql """insert into string_min_max values (1,'name1'), (2, 'name2')"""
    sql """analyze table string_min_max with sync"""
    explain {
        sql("select min(name), max(name) from string_min_max")
        contains "pushAggOp=NONE"
    }
    sql """set enable_pushdown_string_minmax = true"""
    explain {
        sql("select min(name), max(name) from string_min_max")
        contains "pushAggOp=MINMAX"
    }

    // Test alter
    sql """
      CREATE TABLE alter_test(
       `id`      int NOT NULL,
       `name`     VARCHAR(25) NOT NULL
      )ENGINE=OLAP
      DUPLICATE KEY(`id`)
      COMMENT "OLAP"
      DISTRIBUTED BY HASH(`id`) BUCKETS 1
      PROPERTIES (
       "replication_num" = "1"
      );
   """
    sql """ANALYZE TABLE alter_test WITH SYNC"""
    def alter_result = sql """show table stats alter_test"""
    assertEquals("false", alter_result[0][7])
    sql """alter table alter_test modify column id set stats ('row_count'='2.0E7', 'ndv'='3927659.0', 'num_nulls'='0.0', 'data_size'='2.69975443E8', 'min_value'='1', 'max_value'='2');"""
    alter_result = sql """show table stats alter_test"""
    assertEquals("true", alter_result[0][7])
    sql """ANALYZE TABLE alter_test WITH SYNC"""
    alter_result = sql """show table stats alter_test"""
    assertEquals("false", alter_result[0][7])
    sql """alter table alter_test modify column id set stats ('row_count'='2.0E7', 'ndv'='3927659.0', 'num_nulls'='0.0', 'data_size'='2.69975443E8', 'min_value'='1', 'max_value'='2');"""
    alter_result = sql """show table stats alter_test"""
    assertEquals("true", alter_result[0][7])
    sql """drop stats alter_test"""
    alter_result = sql """show table stats alter_test"""
    assertEquals("", alter_result[0][7])
    sql """alter table alter_test modify column id set stats ('row_count'='100', 'ndv'='0', 'num_nulls'='0.0', 'data_size'='2.69975443E8', 'min_value'='1', 'max_value'='2');"""
    alter_result = sql """show column stats alter_test(id)"""
    logger.info("show column alter_test(id) stats: " + alter_result)
    assertEquals(1, alter_result.size())
    alter_result = sql """show column cached stats alter_test(id)"""
    assertEquals(1, alter_result.size())
    sql """alter table alter_test modify column id set stats ('row_count'='100', 'ndv'='0', 'num_nulls'='100', 'data_size'='2.69975443E8', 'min_value'='1', 'max_value'='2');"""
    alter_result = sql """show column stats alter_test(id)"""
    logger.info("show column alter_test(id) stats: " + alter_result)
    assertEquals(1, alter_result.size())
    alter_result = sql """show column cached stats alter_test(id)"""
    assertEquals(1, alter_result.size())
    sql """alter table alter_test modify column id set stats ('row_count'='100', 'ndv'='1', 'num_nulls'='0', 'data_size'='2.69975443E8', 'min_value'='1', 'max_value'='2');"""
    alter_result = sql """show column stats alter_test(id)"""
    logger.info("show column alter_test(id) stats: " + alter_result)
    assertEquals(1, alter_result.size())
    alter_result = sql """show column cached stats alter_test(id)"""
    assertEquals(1, alter_result.size())

    // Test trigger type, manual default full, manual high health value, sample empty, kill job, show analyze
    sql """DROP DATABASE IF EXISTS trigger"""
    sql """CREATE DATABASE IF NOT EXISTS trigger"""
    sql """USE trigger"""
    sql """
     CREATE TABLE if not exists trigger_test(
      `id`      int NOT NULL,
      `name`    VARCHAR(152)
     )ENGINE=OLAP
     DUPLICATE KEY(`id`)
     COMMENT "OLAP"
     DISTRIBUTED BY HASH(`id`) BUCKETS 1
     PROPERTIES (
      "replication_num" = "1"
     );
   """
    // Test sample empty table
    def result_sample = sql """analyze table trigger_test with sample percent 10 with sync"""
    result_sample = sql """show column stats trigger_test"""
    logger.info("show column trigger_test stats: " + result_sample)
    assertEquals(2, result_sample.size())
    assertEquals("0.0", result_sample[0][2])
    assertEquals("SAMPLE", result_sample[0][9])
    assertEquals("0.0", result_sample[1][2])
    assertEquals("SAMPLE", result_sample[1][9])

    sql """drop stats trigger_test"""
    sql """analyze table trigger_test with sample rows 1000 with sync"""
    result_sample = sql """show column stats trigger_test"""
    logger.info("show column trigger_test stats: " + result_sample)
    assertEquals(2, result_sample.size())
    assertEquals("0.0", result_sample[0][2])
    assertEquals("SAMPLE", result_sample[0][9])
    assertEquals("0.0", result_sample[1][2])
    assertEquals("SAMPLE", result_sample[1][9])

    // Test trigger type
    sql """drop stats trigger_test"""
    sql """insert into trigger_test values(1,'name1') """
    sql """insert into trigger_test values(2,'name2') """
    sql """insert into trigger_test values(3,'name3') """
    sql """insert into trigger_test values(4,'name4') """

    // Test analyze default full.
    sql """analyze table trigger_test with sync"""
    result = sql """show column stats trigger_test"""
    logger.info("show column trigger_test stats: " + result)
    assertEquals(2, result.size())
    assertEquals("4.0", result[0][2])
    assertEquals("FULL", result[0][9])
    assertEquals("4.0", result[1][2])
    assertEquals("FULL", result[1][9])

    // Test manual analyze ignore health value
    sql """insert into trigger_test values(5,'name5') """
    sql """analyze table trigger_test with sync"""
    result = sql """show column stats trigger_test"""
    logger.info("show column trigger_test stats: " + result)
    assertEquals(2, result.size())
    assertEquals("5.0", result[0][2])
    assertEquals("5.0", result[1][2])

    // Test auto analyze with job type SYSTEM
    sql """drop stats trigger_test"""
    sql """analyze table trigger_test PROPERTIES("use.auto.analyzer"="true")"""
    int i = 0;
    for (0; i < 10; i++) {
        result = sql """show column stats trigger_test"""
        if (result.size() != 2) {
            Thread.sleep(1000)
            continue;
        }
        assertEquals(result[0][11], "SYSTEM")
        assertEquals(result[1][11], "SYSTEM")
        break
    }
    if (i < 10) {
        sql """analyze table trigger_test with sync"""
        result = sql """show column stats trigger_test"""
        assertEquals(result.size(), 2)
        assertEquals(result[0][11], "MANUAL")
        assertEquals(result[1][11], "MANUAL")
    }

    // Test show task
    result_sample = sql """analyze table trigger_test with sample percent 10"""
    String jobId = result_sample[0][0]
    result_sample = sql """show analyze task status ${jobId}"""
    assertEquals(2, result_sample.size())

    // Test inject stats avg_size.
    sql """CREATE TABLE `date_dim` (
          `d_date_sk` BIGINT NOT NULL,
          `d_date_id` CHAR(16) NOT NULL,
          `d_date` DATE NULL,
          `d_month_seq` INT NULL,
          `d_week_seq` INT NULL,
          `d_quarter_seq` INT NULL,
          `d_year` INT NULL,
          `d_dow` INT NULL,
          `d_moy` INT NULL,
          `d_dom` INT NULL,
          `d_qoy` INT NULL,
          `d_fy_year` INT NULL,
          `d_fy_quarter_seq` INT NULL,
          `d_fy_week_seq` INT NULL,
          `d_day_name` CHAR(9) NULL,
          `d_quarter_name` CHAR(6) NULL,
          `d_holiday` CHAR(1) NULL,
          `d_weekend` CHAR(1) NULL,
          `d_following_holiday` CHAR(1) NULL,
          `d_first_dom` INT NULL,
          `d_last_dom` INT NULL,
          `d_same_day_ly` INT NULL,
          `d_same_day_lq` INT NULL,
          `d_current_day` CHAR(1) NULL,
          `d_current_week` CHAR(1) NULL,
          `d_current_month` CHAR(1) NULL,
          `d_current_quarter` CHAR(1) NULL,
          `d_current_year` CHAR(1) NULL
        ) ENGINE=OLAP
        DUPLICATE KEY(`d_date_sk`)
        DISTRIBUTED BY HASH(`d_date_sk`) BUCKETS 12
        PROPERTIES (
        "replication_allocation" = "tag.location.default: 1")
    """

    sql """
        alter table date_dim modify column d_day_name set stats ('row_count'='73049', 'ndv'='7', 'num_nulls'='0', 'min_value'='Friday', 'max_value'='Wednesday', 'data_size'='521779')
    """

    alter_result = sql """show column cached stats date_dim"""
    assertEquals("d_day_name", alter_result[0][0])
    assertEquals("date_dim", alter_result[0][1])
    assertEquals("73049.0", alter_result[0][2])
    assertEquals("7.0", alter_result[0][3])
    assertEquals("0.0", alter_result[0][4])
    assertEquals("521779.0", alter_result[0][5])
    assertEquals("7.142863009760572", alter_result[0][6])

    sql """DROP DATABASE IF EXISTS trigger"""

    // Test show last analyze table version
    sql """create database if not exists test_version"""
    sql """use test_version"""
    sql """drop table if exists region"""
    sql """
        CREATE TABLE region  (
            r_regionkey      int NOT NULL,
            r_name       VARCHAR(25) NOT NULL,
            r_comment    VARCHAR(152)
        )ENGINE=OLAP
        DUPLICATE KEY(`r_regionkey`)
        COMMENT "OLAP"
        DISTRIBUTED BY HASH(`r_regionkey`) BUCKETS 1
        PROPERTIES (
            "replication_num" = "1" 
        );
    """
    sql """analyze table region with sync"""
    def versionResult = sql """show column stats region"""
    assertEquals(versionResult[0][16], "1")
    assertEquals(versionResult[1][16], "1")
    assertEquals(versionResult[2][16], "1")

    sql """insert into region values (1, "1", "1")"""
    sql """analyze table region with sync"""
    versionResult = sql """show column stats region"""
    assertEquals(versionResult[0][16], "2")
    assertEquals(versionResult[1][16], "2")
    assertEquals(versionResult[2][16], "2")

    sql """drop database if exists test_version"""
}
