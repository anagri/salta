class Membership < ActiveRecord::Base
  belongs_to :contact, :class_name => 'User'
  belongs_to :group
end