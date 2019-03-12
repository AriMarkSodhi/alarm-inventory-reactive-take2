   module: ietf-hardware
     +--rw hardware
        +--ro last-change?   yang:date-and-time
        +--rw component* [name]
           +--rw name              string
           +--rw class             identityref
           +--ro physical-index?   int32 {entity-mib}?
           +--ro description?      string
           +--rw parent?           -> ../../component/name
           +--rw parent-rel-pos?   int32
           +--ro contains-child*   -> ../../component/name
           +--ro hardware-rev?     string
           +--ro firmware-rev?     string
           +--ro software-rev?     string
           +--ro serial-num?       string
           +--ro mfg-name?         string
           +--ro model-name?       string
           +--rw alias?            string
           +--rw asset-id?         string
           +--ro is-fru?           boolean
           +--ro mfg-date?         yang:date-and-time

Bierman, et al.              Standards Track                    [Page 4]
RFC 8348                YANG Hardware Management              March 2018

           +--rw uri*              inet:uri
           +--ro uuid?             yang:uuid
           +--rw state {hardware-state}?
           |  +--ro state-last-changed?   yang:date-and-time
           |  +--rw admin-state?          admin-state
           |  +--ro oper-state?           oper-state
           |  +--ro usage-state?          usage-state
           |  +--ro alarm-state?          alarm-state
           |  +--ro standby-state?        standby-state
           +--ro sensor-data {hardware-sensor}?
              +--ro value?               sensor-value
              +--ro value-type?          sensor-value-type
              +--ro value-scale?         sensor-value-scale
              +--ro value-precision?     sensor-value-precision
              +--ro oper-status?         sensor-status
              +--ro units-display?       string
              +--ro value-timestamp?     yang:date-and-time
              +--ro value-update-rate?   uint32