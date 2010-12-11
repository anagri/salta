class Membership < ActiveRecord::Base
  belongs_to :contact, :class_name => User.name
  belongs_to :group
end